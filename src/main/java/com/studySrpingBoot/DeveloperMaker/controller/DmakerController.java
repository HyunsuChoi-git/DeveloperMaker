package com.studySrpingBoot.DeveloperMaker.controller;

import com.studySrpingBoot.DeveloperMaker.dto.CreateDeveloper;
import com.studySrpingBoot.DeveloperMaker.dto.DeveloperDetailDto;
import com.studySrpingBoot.DeveloperMaker.dto.DeveloperDto;
import com.studySrpingBoot.DeveloperMaker.dto.EditDeveloper;
import com.studySrpingBoot.DeveloperMaker.service.DmakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/create-developer")
    public CreateDeveloper.Response createDevelopers(
            @Valid @RequestBody CreateDeveloper.Request request
            //@Valid어노테이션을 넣어줌으로써 dto에 걸어놨던 데이터검증(Nonnull,Size,Min 등)을 해준다.
            ) {
        log.info("request : "+request);

        return dmakerService.createDeveloper(request);

    }

    @DeleteMapping("developer/{memberId}")
    public DeveloperDetailDto deleteDeveloper(@PathVariable  String memberId){
        return dmakerService.deleteCeveloper(memberId);
    }

}
