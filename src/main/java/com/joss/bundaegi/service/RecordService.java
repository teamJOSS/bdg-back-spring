package com.joss.bundaegi.service;

import com.joss.bundaegi.domain.RecordDomain;
import com.joss.bundaegi.domain.Response.JSONResponse;
import com.joss.bundaegi.mapper.RecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class RecordService {
    @Autowired
    RecordMapper recordMapper;

    // 내 진료기록 조회
    public JSONResponse<List<RecordDomain>> getRecordById(String id) {
        JSONResponse<List<RecordDomain>> response;
        try{
            List<RecordDomain> records = recordMapper.getRecordById(id);
            if(records.size() > 0) response = new JSONResponse<>(1,"succ.select",records);
            else response = new JSONResponse<>(1,"succ.record.empty",null);
        }catch (Exception e){
            response = new JSONResponse<>(0,"fail.select",null);
        }
        return response;
    }
    @Transactional
    // 진료기록 생성
    public JSONResponse<Integer> createRecordInfo(Map<String,Object> paramMap){
        JSONResponse<Integer> response;
        try{
            int result = recordMapper.createRecordInfo(paramMap);
            if(result == 1) response = new JSONResponse<>(1,"succ.insert",result);
            else throw new Exception();
        }catch (Exception e){
            response = new JSONResponse<>(0,"fail.insert",0);
        }
        return response;
    }
    @Transactional
    // 진료결과 업데이트
    public JSONResponse<Integer> updateRecordResult(Map<String,Object> paramMap){
        JSONResponse<Integer> response;
        try{
            int result = recordMapper.updateRecordResult(paramMap);
            if(result == 1) response = new JSONResponse<>(1,"succ.record.result",result);
            else throw new Exception();
        }
        catch (Exception e){
            response = new JSONResponse<>(0,"fail.record.update",0);
        }
        return response;
    }
}
