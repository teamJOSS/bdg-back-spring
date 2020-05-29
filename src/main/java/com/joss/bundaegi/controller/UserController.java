package com.joss.bundaegi.controller;

import com.joss.bundaegi.domain.Jwt.JwtRequest;
import com.joss.bundaegi.domain.RestException;
import com.joss.bundaegi.domain.UserDomain;
import com.joss.bundaegi.domain.Response.JSONResponse;
import com.joss.bundaegi.service.UserService;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserService userService;

    // 전체 사용자 조회
    @GetMapping(value = "/user")
    public JSONResponse<List<UserDomain>> getAllUser() {
        return userService.getAllUser();
    }

    // 사용자 조회
    @GetMapping(value = "/user/{id}")
    public JSONResponse<UserDomain> getUserById(@PathVariable("id") final String id) {
        return userService.getUserById(id);
    }

    // 사용자 등록
    @PostMapping(value = "/user")
    public JSONResponse<String> createUser(@RequestBody Map<String,Object> paramMap) {
        return userService.createUser(paramMap);
    }

    // 로그인
    @PostMapping(value = "/user/login")
    public JSONResponse login(@RequestBody JwtRequest authenticationRequest){
        return userService.getLoginInfo(authenticationRequest);
    }
}
