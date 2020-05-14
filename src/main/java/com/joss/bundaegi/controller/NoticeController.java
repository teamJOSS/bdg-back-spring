package com.joss.bundaegi.controller;

import com.joss.bundaegi.domain.ClinicDomain;
import com.joss.bundaegi.domain.LocationDomain;
import com.joss.bundaegi.domain.NoticeDomain;
import com.joss.bundaegi.domain.Response.JSONResponse;
import com.joss.bundaegi.service.ClinicService;
import com.joss.bundaegi.service.CommonService;
import com.joss.bundaegi.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class NoticeController {
    @Autowired
    NoticeService noticeService;

    // 공지사항 조회
    @GetMapping(value = "/notice")
    public JSONResponse<List<NoticeDomain>> getNoticeInfo() {
        return noticeService.getNoticeInfo();
    }

    // 공지사항 저장
    @PostMapping(value = "/notice")
    public JSONResponse<Integer> createNoticeInfo(@RequestBody Map<String,Object> paramMap){
        return noticeService.createNoticeInfo(paramMap);
    }
}
