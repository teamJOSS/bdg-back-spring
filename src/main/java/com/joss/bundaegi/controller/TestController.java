package com.joss.bundaegi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@Controller
public class TestController {
    @Autowired
    Environment environment;

    @GetMapping(value = "/profiles")
    @ResponseStatus(value = HttpStatus.OK)
    public String Running(){
        return Arrays.toString(environment.getActiveProfiles());
    }

    @RequestMapping(value = "/map")
    public ModelAndView test(){
        return new ModelAndView("test");
    }
}
