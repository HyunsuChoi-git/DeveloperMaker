package com.studySrpingBoot.DeveloperMaker.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCode {
    EMPLOYED("고용"),
    RETIRED("퇴직");

    public final String description;
}
