package com.joss.bundaegi.service;

import com.joss.bundaegi.domain.ClinicDomain;
import com.joss.bundaegi.domain.LocationDomain;
import com.joss.bundaegi.domain.Response.JSONResponse;
import com.joss.bundaegi.mapper.ClinicMapper;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ClinicService {
    @Autowired
    ClinicMapper clinicMapper;

    // 주변 진료소 조회
    public JSONResponse<List<ClinicDomain>> getClinicByDistance(Map<String,Object> paramMap, float distance) {
        JSONResponse<List<ClinicDomain>> response;
        try{
            paramMap.put("distance",distance);
            List<ClinicDomain> clinics = clinicMapper.getClinicByDistance(paramMap);
            System.out.println(clinics.size());
            if(clinics.size() > 0) response = new JSONResponse<>(1,"succ.select",clinics);
            else throw new Exception();
        }catch (Exception e){
            response = new JSONResponse<>(0,"fail.select",null);
        }
        return response;
    }
    public int updateLocationInfo(String key, float lat, float lon){
        try{
            int result = clinicMapper.updateLocationInfo(key,lat,lon);
            System.out.println("업데이트 결과 " + result);
            if(result == 0) throw new Exception();
        }
        catch (Exception e){
            System.out.println(e.toString());
            return 0;
        }
        return 1;
    }
}
