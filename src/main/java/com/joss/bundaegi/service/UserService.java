package com.joss.bundaegi.service;

import com.joss.bundaegi.domain.UserDomain;
import com.joss.bundaegi.domain.Response.JSONResponse;
import com.joss.bundaegi.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class UserService {
    @Autowired
    UserMapper userMapper;

    public JSONResponse<List<UserDomain>> getAllUser() {
        JSONResponse<List<UserDomain>> response;
        try{
            List<UserDomain> users = userMapper.getAllUser();
            if(!users.isEmpty()) response = new JSONResponse<>(1,"succ.select",users);
            else throw new Exception();
        }catch (Exception e){
            response = new JSONResponse<>(0,"fail.select",null);
        }
        return response;
    }
    public JSONResponse<UserDomain> getUserById(final String id) {
        JSONResponse<UserDomain> response;
        try{
            UserDomain user = userMapper.getUser(id);
            if(user != null) response = new JSONResponse<>(1,"succ.select",user);
            else response = new JSONResponse<>(0,"fail.select.user",null);
        }catch (Exception e){
            response = new JSONResponse<>(0,"fail.login.info",null);
        }
        return response;
    }

    public JSONResponse<String> createUser(Map<String,Object> paramMap){
        JSONResponse<String> response;
        try{
            UserDomain user = userMapper.getUser((String) paramMap.get("userId"));
            if(user == null) {
                int result = userMapper.createUser(paramMap);
                if(result == 1) response = new JSONResponse<>(1,"succ.reg",null);
                else throw new Exception();
            }
            else response = new JSONResponse<>(0,"fail.reg.info",null);
        }catch(Exception e){
            response = new JSONResponse<>(0,"fail.reg",null);
        }
        return response;
    }

    // @TODO 로그인 인증처리 하면서 같이 할것(JWT)
//    public JSONResponse<Integer> getLoginInfo() {
//        JSONResponse<Integer> response;
//        try{
//            int result = userMapper.getLoginInfo();
//            if(result == 1) response = new JSONResponse<>(1,"succ.login",null);
//            else throw new Exception();
//        }catch (Exception e){
//            response = new JSONResponse<>(0,"fail.select",null);
//        }
//        return response;
//    }
}
