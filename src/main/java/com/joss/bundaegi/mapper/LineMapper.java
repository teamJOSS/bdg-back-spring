package com.joss.bundaegi.mapper;

import com.joss.bundaegi.domain.ClinicDomain;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Mapper
@Component
@Repository
public interface LineMapper {
    int getMyWaitingCount(Map<String,Object> paramMap);
    ClinicDomain getMyWaitingClinic(String userId);
    int isWaiting(Map<String,Object> paramMap);
    int cancelLineState(Map<String,Object> paramMap);
    int insertLine(Map<String,Object> paramMap);

//    int updateLineStateStart(Map<String,Object> paramMap);
//    int updateLineStateEnd(Map<String,Object> paramMap);
}
