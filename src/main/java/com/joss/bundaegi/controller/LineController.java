package com.joss.bundaegi.controller;

import com.joss.bundaegi.domain.ClinicDomain;
import com.joss.bundaegi.domain.LocationDomain;
import com.joss.bundaegi.domain.Response.JSONResponse;
import com.joss.bundaegi.service.ClinicService;
import com.joss.bundaegi.service.CommonService;
import com.joss.bundaegi.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LineController {
    @Autowired
    LineService lineService;

    // 나의 현황 조회
    @GetMapping(value = "/line/{userId}")
    public JSONResponse<ClinicDomain> getMyWaitingClinic(@PathVariable(value = "userId") String userId){
        return lineService.getMyWaitingClinic(userId);
    }

    //  해당 병원 나의 순번 조회
    @GetMapping(value = "/line/clinic/{clinicId}")
    public JSONResponse<Integer> getMyWaitingCount(@PathVariable(value = "clinicId") String clinicId,
                                                 @RequestParam Map<String,Object> paramMap){
        return lineService.getMyWaitingCount(clinicId, paramMap);
    }
    // 줄서기
    @PostMapping(value = "/line/{clinicId}")
    public JSONResponse<Integer> insertLine(@PathVariable(value = "clinicId") String clinicId,
                                            @RequestParam Map<String,Object> paramMap){
        return lineService.insertLine(clinicId,paramMap);
    }
    // 줄서기 취소
    @PatchMapping(value = "/line/{clinicId}")
    public JSONResponse<Integer> setLineStateCancel(@PathVariable(value = "clinicId") String clinicId,
                                                    @RequestParam Map<String,Object> paramMap) {
        return lineService.cancelLineState(clinicId, paramMap);
    }

//    // 줄서기 시작
//    @PatchMapping(value = "/line/{clinicId}")
//    public JSONResponse<Integer> setLineStateCancel(@PathVariable(value = "clinicId") String clinicId) {
//        return lineService.cancelLineState()
//    }
//    // 줄서기 종료
//    @PatchMapping(value = "/line/{clinicId}")
//    public JSONResponse<Integer> setLineStateCancel(@PathVariable(value = "clinicId") String clinicId) {
//        return lineService.cancelLineState()
//    }
}
