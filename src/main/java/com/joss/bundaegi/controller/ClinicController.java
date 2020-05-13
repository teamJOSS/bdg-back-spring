package com.joss.bundaegi.controller;

import com.joss.bundaegi.domain.ClinicDomain;
import com.joss.bundaegi.domain.LocationDomain;
import com.joss.bundaegi.domain.Response.JSONResponse;
import com.joss.bundaegi.service.ClinicService;
import com.joss.bundaegi.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // 주변 진료소 조회
    @GetMapping(value = "/clinic/location/{distance}")
    public JSONResponse<List<ClinicDomain>> getLocationFromAddr(@PathVariable(value = "distance") final float distance, LocationDomain locationDomain) {
        return clinicService.getClinicByDistance(locationDomain,distance);
    }
}
