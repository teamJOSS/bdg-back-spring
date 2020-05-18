package com.joss.bundaegi.mapper;

import com.joss.bundaegi.domain.ClinicDomain;
import com.joss.bundaegi.domain.UserDomain;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Component
@Repository
public interface ClinicMapper {
    List<ClinicDomain> getClinicByDistance(Map<String,Object> paramMap);
    ClinicDomain getClinicById(String clinicId);
    int updateLocationInfo(String key, float lat, float lon);
}
