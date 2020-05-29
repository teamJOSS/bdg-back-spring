package com.joss.bundaegi.controller;

import com.joss.bundaegi.domain.NoticeDomain;
import com.joss.bundaegi.domain.Response.JSONResponse;
import com.joss.bundaegi.domain.VirusDomain;
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

    // 바이러스 정보 조회
    @GetMapping(value = "/virus/{virusId}")
    public JSONResponse<VirusDomain> getVirusInfo(@PathVariable(value = "virusId") String virusId){
        return noticeService.getVirusInfo(virusId);
    }
}
