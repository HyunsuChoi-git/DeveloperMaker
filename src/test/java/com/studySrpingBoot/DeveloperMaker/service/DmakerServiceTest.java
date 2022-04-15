package com.studySrpingBoot.DeveloperMaker.service;

import com.studySrpingBoot.DeveloperMaker.dto.CreateDeveloper;
import com.studySrpingBoot.DeveloperMaker.dto.DeveloperDetailDto;
import com.studySrpingBoot.DeveloperMaker.dto.DeveloperDto;
import com.studySrpingBoot.DeveloperMaker.entity.Developer;
import com.studySrpingBoot.DeveloperMaker.repository.DeveloperRepository;
import com.studySrpingBoot.DeveloperMaker.repository.RetiredDeveloperRepository;
import com.studySrpingBoot.DeveloperMaker.type.DeveloperLevel;
import com.studySrpingBoot.DeveloperMaker.type.DeveloperSkillType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

//통합테스트에 많이 사용. 실제환경과 같이 Bean을 전부 띄우고 테스트
//@SpringBootTest

//Spring서버를 띄우지 않고, Java환경에서 테스트하기 위해 외부기능(모키토익스텐션)을 사용한다고 선언하는 것
@ExtendWith(MockitoExtension.class)
class DmakerServiceTest {

    //@InjectMocks 클래스에서 참조하고 있는 클래스를 가상의 @Mock으로 만들어줌
    @Mock
    private DeveloperRepository developerRepository;
    @Mock
    private RetiredDeveloperRepository retiredDeveloperRepository;

    //@Autowired
    @InjectMocks   //가짜 객체 생성. 객체생성하면서 위에 Mock을 같이 가져와줌.
    private DmakerService dmakerService;

    @Test
    public void getDeveloperDetail(){
        // 위에 지정한 Mock에 대해서 동작을 지정해 주어야 함.
        // 메소드에 anyString()를 넣으면 willReturn 하위 결과값을 받겠다고 임시로 지정해놓은 것.
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(Developer.builder()
                        .age(20)
                        .developerLevel(DeveloperLevel.JUNIOR)
                        .developerSkillType(DeveloperSkillType.BACK_END)
                        .name("hera")
                        .memberId("00001")
                        .experienceYear(12)
                        .build()));

        DeveloperDetailDto developerDetail = dmakerService.getDeveloper("memberId");
        assertEquals(DeveloperLevel.JUNIOR, developerDetail.getDeveloperLevel());
        assertEquals(DeveloperSkillType.BACK_END, developerDetail.getDeveloperSkillType());
        assertEquals(12, developerDetail.getExperienceYears());
    }


    @Test
    public void getAllEmployedDevelopers(){
        dmakerService.createDeveloper(CreateDeveloper.Request.builder()
                        .age(20)
                        .developerLevel(DeveloperLevel.JUNIOR)
                        .developerSkillType(DeveloperSkillType.BACK_END)
                        .name("hera")
                        .memberId("00001")
                        .experienceYears(3)
                .build());
        List<DeveloperDto> allEmployedDevelopers = dmakerService.getAllEmployedDevelopers();
        System.out.println("===========================");
        System.out.println(allEmployedDevelopers);
        System.out.println("===========================");
    }

    @Test
    public void testSomething(){
        String result = "hello" + " word";

        assertEquals("hello word", result);
    }

}