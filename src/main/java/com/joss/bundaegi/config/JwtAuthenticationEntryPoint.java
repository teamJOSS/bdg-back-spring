package com.joss.bundaegi.config;

import com.joss.bundaegi.domain.RestException;
import com.joss.bundaegi.utils.MessageUtils;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -7858869558953243875L;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        // httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        String message = MessageUtils.getMessage(httpServletRequest.getAttribute("message").toString());
        HttpStatus status = (HttpStatus) httpServletRequest.getAttribute("status");
        String detail = httpServletRequest.getAttribute("detail").toString();
        System.out.println(message);
        System.out.println(detail);
        JSONObject json = new JSONObject();
        json.put("code",0);
        json.put("message",message);
        json.put("detail",detail);
        json.put("status",status);
        PrintWriter out = httpServletResponse.getWriter();
        out.print(json);
    }
}
