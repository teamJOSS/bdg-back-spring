package com.joss.bundaegi.service;

import com.joss.bundaegi.domain.Jwt.JwtRequest;
import com.joss.bundaegi.domain.RestException;
import com.joss.bundaegi.domain.UserDomain;
import com.joss.bundaegi.domain.Response.JSONResponse;
import com.joss.bundaegi.mapper.UserMapper;
import com.joss.bundaegi.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.CredentialException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    AuthenticationManager authenticationManager;

    public JSONResponse<List<UserDomain>> getAllUser() {
        JSONResponse<List<UserDomain>> response;
        try{
            List<UserDomain> users = userMapper.getAllUser();
            if(!users.isEmpty()) response = new JSONResponse<>(1,"succ.select",users);
            else response = new JSONResponse<>(1,"succ.select.empty",null);
        }catch (Exception e){
            throw new RestException(0,"fail.select",e.getLocalizedMessage(),null);
//            response = new JSONResponse<>(0,"fail.select",null);
        }
        return response;
    }
    public JSONResponse<UserDomain> getUserById(final String id) {
        JSONResponse<UserDomain> response;
        try{
            UserDomain user = userMapper.getUser(id);
            if(user != null) response = new JSONResponse<>(1,"succ.select",user);
            else response = new JSONResponse<>(1,"succ.select.empty",null);
        }catch (Exception e){
            throw new RestException(0,"fail.select",e.getLocalizedMessage(),null);
//            response = new JSONResponse<>(0,"fail.login.info",null);
        }
        return response;
    }
    @Transactional
    public JSONResponse createUser(Map<String,Object> paramMap){
        JSONResponse response;
        try{
            UserDomain user = userMapper.getUser((String) paramMap.get("userId"));
            if(user == null) {
                if(!isValidUserInfo(paramMap)) throw new RestException(0,"fail.reg.empty","",HttpStatus.BAD_REQUEST);
                int result = userMapper.createUser(paramMap);
                if(result == 1) {
                    String userId = (String) paramMap.get("userId");
                    String userPassword = (String)paramMap.get("userPassword");
                    String token = createToken(userId,userPassword);
                    Map<String, Object> map = new HashMap<>();
                    map.put("token",token);
                    map.put("user",userMapper.getUser(userId));
                    response = new JSONResponse<>(1,"succ.reg",map);
                } else {
                    throw new Exception();
                }
            } else {
                throw new RestException(0,"fail.reg.info","",HttpStatus.NOT_FOUND);
            }
            //else response = new JSONResponse<>(0,"fail.reg.info",null);
        }catch (RestException e){
            System.out.println("RestException e");
            System.out.println(e.getLocalizedMessage());
            throw e;
        }
        catch(Exception e){
            System.out.println("Exception e");
            System.out.println(e.getLocalizedMessage());
            throw new RestException(0,"fail.reg",e.getLocalizedMessage(),HttpStatus.EXPECTATION_FAILED);
        }
        return response;
    }

    // 로그인 처리
    public JSONResponse getLoginInfo(JwtRequest request) {
        JSONResponse response;
        try{
            final String token = createToken(request.getUserId(),request.getUserPassword());
            Map<String, Object> map = new HashMap<>();
            map.put("token",token);
            map.put("user",userMapper.getUser(request.getUserId()));
            response = new JSONResponse<>(1,"succ.login",map);
        }
        catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            throw new RestException(0,"fail.login.info",e.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
        }
        return response;
    }

    public String createToken(String userId, String userPassword){
        final UserDetails userDetails = loadUserByUsername(userId);
        if(!passwordEncoder.matches(userPassword, userDetails.getPassword())){
            throw new RestException(0,"fail.login.info","",HttpStatus.BAD_REQUEST);
        }
        final String token = jwtTokenUtil.generateToken(userDetails);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userId, userPassword));
        return token;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username == null) throw new RestException(0,"fail.login.info","",HttpStatus.BAD_REQUEST);
        UserDomain user = userMapper.getUser(username);
        if(user != null){
            // There is no PasswordEncoder mapped for the id "null" 에러 때문에 Password에 "{noop}" 를 붙임
            return new User(user.getUserId(), "{noop}"+ user.getUserPassword(), new ArrayList<>());
        }
        else {
            throw new RestException(0,"fail.login.info","",HttpStatus.BAD_REQUEST);
        }
    }

    public boolean isValidUserInfo(Map<String,Object> userInfo){
        for(Object val : userInfo.values()){
            if("".equals(val)) return false;
        }
        return true;
    }
}
