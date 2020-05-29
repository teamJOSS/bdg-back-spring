package com.joss.bundaegi.service;

import com.joss.bundaegi.domain.NoticeDomain;
import com.joss.bundaegi.domain.Response.JSONResponse;
import com.joss.bundaegi.domain.RestException;
import com.joss.bundaegi.domain.VirusDomain;
import com.joss.bundaegi.mapper.NoticeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class NoticeService {
    @Autowired
    NoticeMapper noticeMapper;

    // 공지사항 조회(1년기준)
    public JSONResponse<List<NoticeDomain>> getNoticeInfo() {
        JSONResponse<List<NoticeDomain>> response;
        try{
            List<NoticeDomain> notices = noticeMapper.getNoticeInfo();
            if(notices.size() > 0) response = new JSONResponse<>(1,"succ.select.notice",notices);
            else response = new JSONResponse<>(0,"fail.select.notice",null);
        }catch (Exception e){
            throw new RestException(0,"fail.select",e.getLocalizedMessage(),null);
        }
        return response;
    }
    @Transactional
    // 공지사항 등록
    public JSONResponse<Integer> createNoticeInfo(Map<String,Object> paramMap){
        JSONResponse<Integer> response;
        try{
            int result = noticeMapper.createNoticeInfo(paramMap);
            if(result == 1) response = new JSONResponse<>(1,"succ.insert",result);
            else throw new Exception();
        }catch (Exception e){
            throw new RestException(0,"fail.insert",e.getLocalizedMessage(),null);
            // response = new JSONResponse<>(0,"fail.insert",0);
        }
        return response;
    }
    public JSONResponse<VirusDomain> getVirusInfo(String virusId){
        JSONResponse<VirusDomain> response;
        try{
            VirusDomain virus = noticeMapper.getVirusInfo(virusId);
            if(virus != null) response = new JSONResponse<>(1,"succ.select.virus",virus);
            else response = new JSONResponse<>(0,"fail.select.virus",null);
        }catch (Exception e){
            throw new RestException(0,"fail.select",e.getLocalizedMessage(),null);
        }
        return response;
    }
}
