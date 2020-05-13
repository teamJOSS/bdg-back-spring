package com.joss.bundaegi.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@RequiredArgsConstructor
public class LineDomain {
    @NotNull String lineSequence;
    @NotNull String lineClinicId;
    @NotNull String lineUserId;
    String lineStateId;
    @NotNull String lineDate;
}