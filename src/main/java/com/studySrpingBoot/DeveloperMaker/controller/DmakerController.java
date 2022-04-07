package com.studySrpingBoot.DeveloperMaker.controller;

import com.studySrpingBoot.DeveloperMaker.service.DmakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

//사용자 입력이 처음 들어오는 곳 Controller
//controller+responseBody(표준 json으로 요청을 받아줌)
@Slf4j
@RestController
@RequiredArgsConstructor
public class DmakerController {
    private final DmakerService dmakerService;

    @GetMapping("/developers")
    public List<String> getAllDevelopers(){
        log.info("GET /developers HTTP/1.1");
        return Arrays.asList("snow","Elsa","Olaf");
    }

    @GetMapping("/create-developer")
    public List<String> createDevelopers(){
        log.info("GET /create-developer HTTP/1.1");

        dmakerService.createDeveloper();

        return Arrays.asList("snow","Elsa","Olaf");
    }
}
