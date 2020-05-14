package com.joss.bundaegi.mapper;

import com.joss.bundaegi.domain.ClinicDomain;
import com.joss.bundaegi.domain.NoticeDomain;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Component
@Repository
public interface NoticeMapper {
    List<NoticeDomain> getNoticeInfo();
    int createNoticeInfo(Map<String,Object> paramMap);
}
