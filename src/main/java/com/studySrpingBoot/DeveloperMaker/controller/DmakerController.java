package com.studySrpingBoot.DeveloperMaker.controller;

import com.studySrpingBoot.DeveloperMaker.dto.*;
import com.studySrpingBoot.DeveloperMaker.exception.DMakerExcepion;
import com.studySrpingBoot.DeveloperMaker.service.DmakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

//사용자 입력이 처음 들어오는 곳 Controller
//controller+responseBody(표준 json으로 요청을 받아줌)
@Slf4j
@RestController
@RequiredArgsConstructor
public class DmakerController {

    private final DmakerService dmakerService;

    @GetMapping("/developers")
    public List<DeveloperDto> getAllDevelopers() {
        log.info("GET /developers HTTP/1.1");
        return dmakerService.getAllEmployedDevelopers();
    }

    @PostMapping("/create-developer")
    public CreateDeveloper.Response createDevelopers(
            @Valid @RequestBody CreateDeveloper.Request request
            //@Valid어노테이션을 넣어줌으로써 dto에 걸어놨던 데이터검증(Nonnull,Size,Min 등)을 해준다.
    ) {
        log.info("request : "+request);

        return dmakerService.createDeveloper(request);

    }

    @GetMapping("/developer/{memberId}")
    public DeveloperDetailDto getDeveloper(
            @PathVariable String memberId) {
        return dmakerService.getDeveloper(memberId);
    }

    @PutMapping("/developer/{memberId}")
    public  DeveloperDetailDto editDeveloper(
            @PathVariable String memberId,
            @Valid @RequestBody EditDeveloper.Request request){
        return dmakerService.editDeveloper(memberId, request);
    }

    @DeleteMapping("developer/{memberId}")
    public DeveloperDetailDto deleteDeveloper(@PathVariable  String memberId){
        return dmakerService.deleteCeveloper(memberId);
    }

    //이걸 안해주면 200 성공으로 응답이 가게 되어있음.
    //         HttpStatus.CONFLICT -> 중복일 때 에러코드
    // 현업에서는 HttpStatus에 딱맞지 않는 에러가 많기 때문에 @ResponseStatus을 쓰기보다는 ErrorResponse에서 코드도 함께 보내준다고 함
    //@ResponseStatus(value = HttpStatus.CONFLICT)
    //이 컨트롤러에서 발생하는 에러를 핸들링 해주는 어노테이션
/*    @ExceptionHandler(DMakerExcepion.class)
    public DMakerErrorResponse handleException(DMakerExcepion e,
                                               HttpServletRequest request){
        log.error("errorCode: {}, url: {}, message: {}",
                e.getDMakerErrorCode(), request.getRequestURI(), e.getMessage());

        return DMakerErrorResponse.builder()
                .errorCode(e.getDMakerErrorCode())
                .errorMessage(e.getMessage())
                .build();
    }*/

}
