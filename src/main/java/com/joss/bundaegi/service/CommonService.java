package com.joss.bundaegi.service;

import com.joss.bundaegi.domain.Common.UserDomain;
import com.joss.bundaegi.domain.Response.JSONResponse;
import com.joss.bundaegi.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommonService {
    @Autowired
    CommonMapper commonMapper;

    public JSONResponse<List<UserDomain>> getAllUser() {
        JSONResponse<List<UserDomain>> response;
        try{
            List<UserDomain> users = commonMapper.getAllUser();
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
            UserDomain user = commonMapper.getUser(id);
            if(user != null) response = new JSONResponse<>(1,"succ.select",user);
            else response = new JSONResponse<>(0,"fail.select.user",null);
        }catch (Exception e){
            response = new JSONResponse<>(0,"fail.login.info",null);
        }
        return response;
    }

    public JSONResponse<String> createUser(final UserDomain newUser){
        JSONResponse<String> response;
        try{
            UserDomain user = commonMapper.getUser(newUser.getUserId());
            if(user != null) {
                int result = commonMapper.createUser(newUser);
                if(result == 1) response = new JSONResponse<>(1,"succ.reg",null);
                else throw new Exception();
            }
            else response = new JSONResponse<>(0,"fail.reg.info",null);
        }catch(Exception e){
            response = new JSONResponse<>(0,"fail.reg",null);
        }
        return response;
    }
}
