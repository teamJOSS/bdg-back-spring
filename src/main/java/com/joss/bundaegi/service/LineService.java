package com.joss.bundaegi.service;

import com.joss.bundaegi.domain.ClinicDomain;
import com.joss.bundaegi.domain.Response.JSONResponse;
import com.joss.bundaegi.domain.RestException;
import com.joss.bundaegi.mapper.LineMapper;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.executor.result.ResultMapException;
import org.apache.ibatis.type.TypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.BindException;
import java.util.Map;

@Service
@Transactional
public class LineService {
    @Autowired
    LineMapper lineMapper;

    // 나의 대기 현황 조회
    public JSONResponse<ClinicDomain> getMyWaitingClinic(String userId){
        JSONResponse<ClinicDomain> response;
        try{
            ClinicDomain clinic = lineMapper.getMyWaitingClinic(userId);
            if(clinic != null) response = new JSONResponse<>(1,"succ.select",clinic);
            else response = new JSONResponse<>(1,"succ.line.my.empty",null);
        }catch (Exception e){
            throw new RestException(0,"fail.select",e.getLocalizedMessage(),null);
//            response = new JSONResponse<>(0,"fail.select",null);
        }
        return response;
    }

    // 나의 대기 순번 조회
    public JSONResponse<Integer> getMyWaitingCount(String clinicId, Map<String,Object> paramMap){
        JSONResponse<Integer> response;
        try{
            paramMap.put("clinicId", clinicId);
            int seq = lineMapper.getMyWaitingCount(paramMap);
            System.out.println("결과값 " + seq);
            if(seq > 0) response = new JSONResponse<>(1,"succ.line",seq);
            else response = new JSONResponse<>(1,"succ.line.empty",seq);
        }
        catch (BindingException e){
            response = new JSONResponse<>(1,"succ.line.my.empty",null);
//            response = new JSONResponse<>(0,"fail.select",null);
        }
        catch (Exception e){
            System.out.println("Exception");
            throw new RestException(0,"fail.select",e.getLocalizedMessage(),null);
//            response = new JSONResponse<>(0,"fail.select",null);
        }
        return response;
    }
    // 줄서기 취소
    public JSONResponse<Integer> cancelLineState(String clinicId, Map<String,Object> paramMap){
        JSONResponse<Integer> response;
        try{
            paramMap.put("clinicId", clinicId);
            int result = lineMapper.cancelLineState(paramMap);
            System.out.println("취소 결과 " + result);
            if(result > 0) response = new JSONResponse<>(1,"succ.line.cancel",result);
            else throw new Exception();
        }
        catch (Exception e){
            throw new RestException(0,"fail.line.cancel",e.getLocalizedMessage(),null);
            // response = new JSONResponse<>(0,"fail.line.cancel",null);
        }
        return response;
    }
    // 줄서기 시작
    public JSONResponse<Integer> insertLine(String clinicId, Map<String,Object> paramMap){
        JSONResponse<Integer> response;
        try{
            int isWaiting = lineMapper.isWaiting(paramMap);
            // 대기할 수 있음
            if(isWaiting == 0) {
                paramMap.put("clinicId", clinicId);
                int result = lineMapper.insertLine(paramMap);
                if(result == 0) throw new Exception();
                response = new JSONResponse<>(1,"succ.line.insert",result);
                System.out.println("시작 결과 " + result);
            }
            else response = new JSONResponse<>(1,"succ.line.busy",0);
        }
        catch (Exception e){
            throw new RestException(0,"fail.line.insert",e.getLocalizedMessage(),null);
            // response = new JSONResponse<>(0,"fail.line.insert",0);
        }
        return response;
    }
//    // 줄서기 시작
//    public int updateLineStateStart(Map<String,Object> paramMap){
//        try{
//            int result = lineMapper.updateLineStateStart(paramMap);
//            System.out.println("시작 결과 " + result);
//            if(result == 0) throw new Exception();
//        }
//        catch (Exception e){
//            System.out.println(e.toString());
//            return 0;
//        }
//        return 1;
//    }
//    // 줄서기 종료
//    public int updateLineStateEnd(Map<String,Object> paramMap){
//        try{
//            int result = lineMapper.updateLineStateEnd(paramMap);
//            System.out.println("종료 결과 " + result);
//            if(result == 0) throw new Exception();
//        }
//        catch (Exception e){
//            System.out.println(e.toString());
//            return 0;
//        }
//        return 1;
//    }
}
