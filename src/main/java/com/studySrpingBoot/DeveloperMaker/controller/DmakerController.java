package com.studySrpingBoot.DeveloperMaker.controller;

import com.studySrpingBoot.DeveloperMaker.dto.CreateDeveloper;
import com.studySrpingBoot.DeveloperMaker.service.DmakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

//사용자 입력이 처음 들어오는 곳 Controller
//controller+responseBody(표준 json으로 요청을 받아줌)
@Slf4j
@RestController
@RequiredArgsConstructor
public class DmakerController {
    private final DmakerService dmakerService;

    @GetMapping("/developers")
    public List<String> getAllDevelopers() {
        log.info("GET /developers HTTP/1.1");
        return Arrays.asList("snow", "Elsa", "Olaf");
    }

    @PostMapping("/create-developer")
    public List<String> createDevelopers(
            @Valid @RequestBody CreateDeveloper.Request request
            //@Valid어노테이션을 넣어줌으로써 dto에 걸어놨던 데이터검증(Nonnull,Size,Min 등)을 해준다.
            ) {
        log.info("request : "+request);

        dmakerService.createDeveloper(request);

        return Arrays.asList("snow", "Elsa", "Olaf");
    }
}
