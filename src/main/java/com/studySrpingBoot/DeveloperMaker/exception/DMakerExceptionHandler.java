package com.studySrpingBoot.DeveloperMaker.exception;

import com.studySrpingBoot.DeveloperMaker.dto.DMakerErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

//Exception handler를 전역으로 관리해주는 클래스
//각 컨트롤러에 advice 역할을 하는 특수한 형태의 빈 어노테이션
@RestControllerAdvice
@Slf4j
public class DMakerExceptionHandler {
    //이걸 안해주면 200 성공으로 응답이 가게 되어있음.
    //         HttpStatus.CONFLICT -> 중복일 때 에러코드
    // 현업에서는 HttpStatus에 딱맞지 않는 에러가 많기 때문에 @ResponseStatus을 쓰기보다는 ErrorResponse에서 코드도 함께 보내준다고 함
    //@ResponseStatus(value = HttpStatus.CONFLICT)
    //이 컨트롤러에서 발생하는 에러를 핸들링 해주는 어노테이션
    @ExceptionHandler(DMakerExcepion.class)
    public DMakerErrorResponse handleException(DMakerExcepion e,
                                               HttpServletRequest request){
        log.error("errorCode: {}, url: {}, message: {}",
                e.getDMakerErrorCode(), request.getRequestURI(), e.getMessage());

        return DMakerErrorResponse.builder()
                .errorCode(e.getDMakerErrorCode())
                .errorMessage(e.getMessage())
                .build();
    }

    //Controller에 진입하기도 전에 발생하는 오류들도 핸들링할 수 있음
    //Exception을 잡아서 ExceptionCode에 있는 정제된 에러메세지로 return
    @ExceptionHandler(value = {
            HttpRequestMethodNotSupportedException.class,    // 경로를 잘못 입력했거나
            MethodArgumentNotValidException.class            // @Valid 검수에 걸린 경우
})
    public DMakerErrorResponse handleBadRequest(
            Exception e, HttpServletRequest request
    ){
        log.error("url: {}, message: {}",
                request.getRequestURI(), e.getMessage());

        return DMakerErrorResponse.builder()
                .errorCode(DMakerErrorCode.INVALID_REQUEST)
                .errorMessage(DMakerErrorCode.INVALID_REQUEST.getMessage())
                .build();
    }
    
    //정의하지 못한 모든 에러를 받아주는 handling 메소드 생성
    @ExceptionHandler(Exception.class)
    public DMakerErrorResponse handleException(
            Exception e, HttpServletRequest request
    ){
        log.error("url: {}, message: {}",
                request.getRequestURI(), e.getMessage());

        return DMakerErrorResponse.builder()
                .errorCode(DMakerErrorCode.INTERNAL_SERVER_ERROR)   //ExceptionCode에 정의한 서버 에러를 넣어줌
                .errorMessage(DMakerErrorCode.INTERNAL_SERVER_ERROR.getMessage())
                .build();
    }

}
