package com.studySrpingBoot.DeveloperMaker.Exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DMakerErrorCode {
    //각 에러 코드를 정의하고 기본이 되는 메세지를 넣어준다)

    NO_DEVELOPER("해당되는 개발자가 없습니다."),
    DUPLICATED_MEMBER_ID("MemverId가 중복됩니다."),
    LEVEL_EXPERIENCE_YEARS_NOT_MATCHED("개발자 레벨과 연차가 일치하지 않습니다."),

    INTERNAL_SERVER_ERROR("서버에 오류 발생"),
    INVALID_REQUEST("잘못된 요청입니다.");


    private final String message;

}
