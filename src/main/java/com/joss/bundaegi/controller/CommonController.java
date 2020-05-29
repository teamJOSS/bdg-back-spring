package com.joss.bundaegi.controller;

import com.joss.bundaegi.domain.Response.JSONResponse;
import com.joss.bundaegi.domain.RestException;
import com.joss.bundaegi.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CommonController {
    @Autowired
    CommonService commonService;
    @GetMapping(value = "/error")
    public void throwException(HttpServletRequest request) {
        System.out.println("throwException " + request.getAttribute("detail"));
        int code = (int)request.getAttribute("code");
        String message = (String)request.getAttribute("message");
        String detail = (String)request.getAttribute("detail");
        HttpStatus status = (HttpStatus)request.getAttribute("status");
        throw new RestException(code,message,detail,status);
    }
}