package com.joss.bundaegi.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class NoticeDomain {
    int noticeSequence;
    String noticeTitle;
    String noticeContent;
    String noticeCreateDate;
    String noticeCreateUser;
}
