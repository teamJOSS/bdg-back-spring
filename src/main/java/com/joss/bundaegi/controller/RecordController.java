package com.joss.bundaegi.controller;

import com.joss.bundaegi.domain.RecordDomain;
import com.joss.bundaegi.domain.Response.JSONResponse;
import com.joss.bundaegi.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RecordController {
    @Autowired
    RecordService recordService;

    // 내 진료기록 조회
    @GetMapping(value = "/clinic/record/{userId}")
    public JSONResponse<List<RecordDomain>> getRecordById(@PathVariable(value = "userId") final String id) {
        return recordService.getRecordById(id);
    }

    // 진료기록 저장
    @PostMapping(value = "/clinic/record")
    public JSONResponse<Integer> createRecordInfo(@RequestBody Map<String,Object> paramMap){
        return recordService.createRecordInfo(paramMap);
    }

    // 진료기록 업데이트
    @PatchMapping(value = "/clinic/record")
    public JSONResponse<Integer> setRecordResult(@RequestBody  Map<String,Object> paramMap) {
        return recordService.updateRecordResult(paramMap);
    }
}
