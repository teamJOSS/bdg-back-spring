package com.joss.bundaegi.mapper;

import com.joss.bundaegi.domain.ClinicDomain;
import com.joss.bundaegi.domain.NoticeDomain;
import com.joss.bundaegi.domain.VirusDomain;
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
    VirusDomain getVirusInfo(String virusId);
    int createNoticeInfo(Map<String,Object> paramMap);
}
