package com.joss.bundaegi.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LocationDomain {
    int code;
    float lat;
    float lon;
}
