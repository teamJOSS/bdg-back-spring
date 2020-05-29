package com.joss.bundaegi.service;

import com.joss.bundaegi.domain.UserDomain;
import com.joss.bundaegi.domain.RestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, RestException {
        if(username == null) throw new RestException(0,"fail.http.badrequest","",HttpStatus.BAD_REQUEST);
        UserDomain user = userService.getUserById(username).getData();
        if(user != null){
            // There is no PasswordEncoder mapped for the id "null" 에러 때문에 Password에 "{noop}" 를 붙임
            return new User(user.getUserId(), "{noop}"+ user.getUserPassword(), new ArrayList<>());
        }
        else {
            throw new RestException(0,"fail.select.user","",HttpStatus.NOT_FOUND);
        }
    }
}
