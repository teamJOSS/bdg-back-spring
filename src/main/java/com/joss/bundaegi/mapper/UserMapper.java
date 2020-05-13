package com.joss.bundaegi.mapper;

import com.joss.bundaegi.domain.UserDomain;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Component
@Repository
public interface UserMapper {
    List<UserDomain> getAllUser();
    UserDomain getUser(String id);
    int createUser(UserDomain user);
}
