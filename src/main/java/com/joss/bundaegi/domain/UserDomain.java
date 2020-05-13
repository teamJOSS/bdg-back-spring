package com.joss.bundaegi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
