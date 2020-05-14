package com.joss.bundaegi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
public class ClinicDomain {
    @NotNull String clinicId;
    @NotNull String clinicLocation;
    String clinicWorkTime;
    @NotNull String clinicPhoneNumber;
    String clinicPhoneNumber2;
    @NotNull String clinicName;
    String clinicType;
    float clinicLat;
    float clinicLon;
}
