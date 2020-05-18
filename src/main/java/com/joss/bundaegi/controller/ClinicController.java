package com.joss.bundaegi.controller;

import com.joss.bundaegi.domain.ClinicDomain;
import com.joss.bundaegi.domain.LocationDomain;
import com.joss.bundaegi.domain.Response.JSONResponse;
import com.joss.bundaegi.service.ClinicService;
import com.joss.bundaegi.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ClinicController {
    @Autowired
    ClinicService clinicService;

    @Autowired
    CommonService commonService;

    // Naver API 호출(위경도 변환)
    @GetMapping(value = "/clinic/addr/{addr}")
    public JSONResponse<LocationDomain> getLocationFromAddr(@PathVariable(value = "addr") final String addr) {
        return commonService.getLocationFromAddr(addr);
    }

    // 해당 주소 위경도 변환 이후 DB UPDATE
    @PatchMapping(value = "/clinic/addr/{addr}")
    public LocationDomain setLocationByAddr(@PathVariable(value = "addr") String addr) {
        LocationDomain domain = commonService.fn_getLocationFromAddr(addr);
        clinicService.updateLocationInfo(addr,domain.getLat(),domain.getLon());
        return domain;
    }

    // 진료소 ID로 조회
    @GetMapping(value = "/clinic/{clinicId}")
    public JSONResponse<ClinicDomain> getClinicById(@PathVariable(value = "clinicId") final String clinicId) {
        return clinicService.getClinicById(clinicId);
    }

    // 주변 진료소 조회
    @GetMapping(value = "/clinic/location/{distance}")
    public JSONResponse<List<ClinicDomain>> getLocationFromAddr(@PathVariable(value = "distance") final float distance,
                                                                @RequestParam Map<String,Object> paramMap) {
        return clinicService.getClinicByDistance(paramMap,distance);
    }
}
