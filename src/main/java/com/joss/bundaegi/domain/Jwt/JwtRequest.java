package com.joss.bundaegi.domain.Jwt;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
public class JwtRequest implements Serializable {
    private static final long serialVersionUID = 5926468583005150707L;
    @NotNull
    private String userId;
    @NotNull
    private String userPassword;
}
