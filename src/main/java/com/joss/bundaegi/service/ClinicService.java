package com.joss.bundaegi.service;

import com.joss.bundaegi.domain.ClinicDomain;
import com.joss.bundaegi.domain.LocationDomain;
import com.joss.bundaegi.domain.Response.JSONResponse;
import com.joss.bundaegi.mapper.ClinicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClinicService {
    @Autowired
    ClinicMapper clinicMapper;

    // 주변 진료소 조회
    public JSONResponse<List<ClinicDomain>> getClinicByDistance(LocationDomain location, float distance) {
        JSONResponse<List<ClinicDomain>> response;
        try{
            List<ClinicDomain> clinics = clinicMapper.getClinicByDistance(location.getLat(),location.getLon(),distance);
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
