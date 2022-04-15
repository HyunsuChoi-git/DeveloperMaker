package com.studySrpingBoot.DeveloperMaker.controller;

import com.studySrpingBoot.DeveloperMaker.dto.DeveloperDto;
import com.studySrpingBoot.DeveloperMaker.service.DmakerService;
import com.studySrpingBoot.DeveloperMaker.type.DeveloperLevel;
import com.studySrpingBoot.DeveloperMaker.type.DeveloperSkillType;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;

//컨트롤러와 관련된 Bean만 올려 테스트할 수 있음
//컨트롤러를 지정해주면 그 컨트롤러와 관련된 Bean만 올려줌)
@WebMvcTest(DmakerController.class)
class DmakerControllerTest {
    @Autowired
    private MockMvc mockMvc;
        // @WebMvcTest에서 제공하는 기능 MockMvc.
        // Controller 호출을 가상으로 도와준다.

    @MockBean  // @ExtendWith(MockitoExtension.class)에 있는 @InjectMocks와 같은 역할 객체생성
    private DmakerService dmakerService;

    //많이 사용할 미디어 타입 지정
    protected MediaType contentType =
            new MediaType(MediaType.APPLICATION_JSON.getType(),
                    MediaType.APPLICATION_JSON.getSubtype(),
                    StandardCharsets.UTF_8);

    @Test
    void getAllDevelopers() throws Exception{
        //목킹작업
        DeveloperDto juniorDeveloperDto = DeveloperDto.builder()
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkillType(DeveloperSkillType.FRONT_END)
                .memberId("0001")
                .build();
        DeveloperDto seniorDeveloperDto = DeveloperDto.builder()
                .developerLevel(DeveloperLevel.SENIOR)
                .developerSkillType(DeveloperSkillType.FULL_STACK)
                .memberId("0002")
                .build();
        given(dmakerService.getAllEmployedDevelopers())
                .willReturn(Arrays.asList(juniorDeveloperDto, seniorDeveloperDto));

        // '/developers' 호출 ( get : org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;)
        // 가짜 mockMvc가 get으로 호출을 하고 컨텐트 타입을 지정한 타입으로 지정한다.
        mockMvc.perform(get("/developers").contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.[0].developerSkillType",
                        is(DeveloperSkillType.FRONT_END.name()))
                ).andExpect(
                        jsonPath("$.[0].developerLevel",
                                is(DeveloperLevel.JUNIOR.name()))
                ).andExpect(
                        jsonPath("$.[1].developerSkillType",
                                is(DeveloperSkillType.FULL_STACK.name()))
                ).andExpect(
                        jsonPath("$.[1].developerLevel",
                                is(DeveloperLevel.SENIOR.name()))
                )
                ;
    }

}