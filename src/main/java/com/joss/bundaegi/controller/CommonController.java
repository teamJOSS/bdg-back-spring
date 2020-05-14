package com.joss.bundaegi.controller;

import com.joss.bundaegi.domain.Response.JSONResponse;
import com.joss.bundaegi.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommonController {
    @Autowired
    CommonService commonService;

}