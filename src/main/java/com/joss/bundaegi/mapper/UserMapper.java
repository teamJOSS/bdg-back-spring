package com.joss.bundaegi.mapper;

import com.joss.bundaegi.domain.UserDomain;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Component
@Repository
public interface UserMapper {
    List<UserDomain> getAllUser();
    UserDomain getUser(String id);
    int createUser(Map<String,Object> paramMap);
    UserDomain getLoginInfo(String userId, String userPassword);
}
