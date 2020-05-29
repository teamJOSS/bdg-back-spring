package com.joss.bundaegi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
public class UserDomain {
    String userId;
    String userPassword;
    String userName;
    String userPhoneNumber;
    String userType;
}
