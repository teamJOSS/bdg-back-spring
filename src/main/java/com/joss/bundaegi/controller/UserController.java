package com.joss.bundaegi.controller;

import com.joss.bundaegi.domain.UserDomain;
import com.joss.bundaegi.domain.Response.JSONResponse;
import com.joss.bundaegi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public JSONResponse<String> createUser(@RequestBody UserDomain user) {
        return userService.createUser(user);
    }

//    // @TODO 로그인 조회
//    @GetMapping(value = "/login/{id,password}")
//    public JSONResponse<List<UserDomain>> getLoginInfo() {
//        return commonService.getLoginInfo();
//    }
}
