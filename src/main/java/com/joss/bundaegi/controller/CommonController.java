package com.joss.bundaegi.controller;

import com.joss.bundaegi.domain.Common.UserDomain;
import com.joss.bundaegi.domain.Response.JSONResponse;
import com.joss.bundaegi.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommonController {
    @Autowired
    CommonService commonService;

    // 전체 사용자 조회
    @GetMapping(value = "/user")
    public JSONResponse<List<UserDomain>> getAllUser() {
        return commonService.getAllUser();
    }
    // 사용자 조회
    @GetMapping(value = "/user/{id}")
    public JSONResponse<UserDomain> getUserById(@PathVariable("id") final String id) {
        return commonService.getUserById(id);
    }

    // 사용자 등록
    @PostMapping(value = "/user")
    public JSONResponse<String> createUser(@RequestBody final UserDomain user) {
        return commonService.createUser(user);
    }
}