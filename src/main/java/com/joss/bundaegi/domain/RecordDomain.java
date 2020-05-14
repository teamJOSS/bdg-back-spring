package com.joss.bundaegi.domain;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RecordDomain {
    int recordSequence;
    String recordUserId;
    String recordEmpId;
    String recordClinicId;
    String recordClinicName;
    String recordClinicPhone;
    String recordResultName;
    String recordDate;
    String recordUdate;
}
