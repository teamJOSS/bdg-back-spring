package com.joss.bundaegi.utils;

import com.joss.bundaegi.domain.Response.JSONResponse;
import com.joss.bundaegi.domain.RestException;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// RestController 에서만 적용 가능
@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(RestException.class)
    public JSONObject handler(RestException e){
        System.out.println("handler");
        JSONObject json = new JSONObject();
        json.put("code",0);
        json.put("message",e.getMessage());
        json.put("detail",e.getDetail());
        json.put("status",e.getStatus());
        return json;
    }
}
