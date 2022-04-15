package com.studySrpingBoot.DeveloperMaker.exception;

import lombok.Getter;

//RuntimeException 상속을 받아 Exception커스텀해보기

@Getter
public class DMakerExcepion extends RuntimeException{
    private DMakerErrorCode dMakerErrorCode;
    private String detailMessage;

    //에러를 받아 그대로 저장해주는 기본 생성자
    public DMakerExcepion(DMakerErrorCode errorCode){
        super(errorCode.getMessage());
        this.dMakerErrorCode = errorCode;
        this.detailMessage = errorCode.getMessage();
    }

    //에러 메세지가 따로 있는 경우(개발자지정) 에정메세지도 같이 받아 저장해주는 생성자
    public DMakerExcepion(DMakerErrorCode errorCode, String detailMessage){
        super(detailMessage);
        this.dMakerErrorCode = errorCode;
        this.detailMessage = detailMessage;
    }
}
