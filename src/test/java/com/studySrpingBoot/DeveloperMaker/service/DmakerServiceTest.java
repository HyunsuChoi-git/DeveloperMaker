package com.studySrpingBoot.DeveloperMaker.service;

import com.studySrpingBoot.DeveloperMaker.dto.CreateDeveloper;
import com.studySrpingBoot.DeveloperMaker.dto.DeveloperDetailDto;
import com.studySrpingBoot.DeveloperMaker.dto.DeveloperDto;
import com.studySrpingBoot.DeveloperMaker.entity.Developer;
import com.studySrpingBoot.DeveloperMaker.exception.DMakerErrorCode;
import com.studySrpingBoot.DeveloperMaker.exception.DMakerException;
import com.studySrpingBoot.DeveloperMaker.repository.DeveloperRepository;
import com.studySrpingBoot.DeveloperMaker.repository.RetiredDeveloperRepository;
import com.studySrpingBoot.DeveloperMaker.type.DeveloperLevel;
import com.studySrpingBoot.DeveloperMaker.type.DeveloperSkillType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

    @Test
    void createDeveloperTest_sucess(){
        //given (목킹, 지역변수 등)
        CreateDeveloper.Request request = CreateDeveloper.Request.builder()
                .age(20)
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkillType(DeveloperSkillType.BACK_END)
                .name("hera")
                .memberId("00001")
                .experienceYears(3)
                .build();

            //생성에 memberId가 중복이면 안되므로 Optional.empty() 를 넣어준다.
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.empty());
            //동작 당시를 캡쳐하여 동작 결과 값을 가져올 수 있다. --> 뒤에 검증에 사용
            //결과 값으로 타입 지정.
            //Create하는 동작을 테스트할 때는 이렇게 캡쳐를 통해 결과값을 검증할 수 있다.
        ArgumentCaptor<Developer> captor =
                ArgumentCaptor.forClass(Developer.class);

        //when (테스트 하고자 하는 동작/ 결과 값)
        CreateDeveloper.Response response = dmakerService.createDeveloper(request);

        //then (예상대로 동작하는 지)
            //verify : 특정 목을 검출, 검증.
            //save 시, 데이터 캡쳐
        verify(developerRepository, times(1))
                .save(captor.capture());
        Developer saveDeveloper = captor.getValue();
        assertEquals(DeveloperLevel.JUNIOR, saveDeveloper.getDeveloperLevel());
        assertEquals(DeveloperSkillType.BACK_END, saveDeveloper.getDeveloperSkillType());
        assertEquals("hera", saveDeveloper.getName());
    }

    @Test
    void createDeveloperTest_fail_DUPLICATED(){
        //given (목킹, 지역변수 등)
        CreateDeveloper.Request request = CreateDeveloper.Request.builder()
                .age(20)
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkillType(DeveloperSkillType.BACK_END)
                .name("hera")
                .memberId("00001")
                .experienceYears(3)
                .build();

            //생성에 memberId가 중복이면 안되므로 결과 값을 넣어준다.
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(Developer.builder()
                        .age(20)
                        .developerLevel(DeveloperLevel.JUNIOR)
                        .developerSkillType(DeveloperSkillType.BACK_END)
                        .name("hera")
                        .memberId("00001")
                        .experienceYear(12)
                        .build()
                ));
            //동작 당시를 캡쳐하여 동작 결과 값을 가져올 수 있다. --> 뒤에 검증에 사용
            //결과 값으로 타입 지정.
            //Create하는 동작을 테스트할 때는 이렇게 캡쳐를 통해 결과값을 검증할 수 있다.
        ArgumentCaptor<Developer> captor =
                ArgumentCaptor.forClass(Developer.class);

        //when (테스트 하고자 하는 동작/ 결과 값)
        CreateDeveloper.Response response = dmakerService.createDeveloper(request);

        //then (예상대로 동작하는 지)
            // 오유로 떨어질 것이기 때문에 동작과 검증을 한번에 해야함.
        DMakerException dMakerExcepion = assertThrows(DMakerException.class,
                () -> dmakerService.createDeveloper(CreateDeveloper.Request.builder()
                .age(20)
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkillType(DeveloperSkillType.BACK_END)
                .name("hera")
                .memberId("00001")
                .experienceYears(12)
                .build()));

        assertEquals(DMakerErrorCode.DUPLICATED_MEMBER_ID, dMakerExcepion.getDMakerErrorCode());
    }
}