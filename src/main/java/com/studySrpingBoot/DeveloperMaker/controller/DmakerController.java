package com.heracomp.study2022springBoot.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

//사용자 입력이 처음 들어오는 곳 Controller
//controller+responseBody(표준 json으로 요청을 받아줌)
@Slf4j
@RestController
public class DmakerController {

    @GetMapping("/developers")
    public List<String> getAllDevelopers(){
        log.info("GET /developers HTTP/1.1");
        return Arrays.asList("snow","Elsa","Olaf");
    }
}
