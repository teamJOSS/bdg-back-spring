package com.joss.bundaegi.mapper;

import com.joss.bundaegi.domain.ClinicDomain;
import com.joss.bundaegi.domain.UserDomain;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Component
@Repository
public interface ClinicMapper {
    List<ClinicDomain> getClinicByDistance(float lat, float lon, float distance);
    int updateLocationInfo(String key, float lat, float lon);
}
