package com.joss.bundaegi.mapper;

import com.joss.bundaegi.domain.RecordDomain;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Component
@Repository
public interface RecordMapper {
    List<RecordDomain> getRecordById(String id);
    int createRecordInfo(Map<String,Object> paramMap);
    int updateRecordResult(Map<String,Object> paramMap);
}
