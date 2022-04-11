package com.studySrpingBoot.DeveloperMaker.service;

import com.studySrpingBoot.DeveloperMaker.Exception.DMakerErrorCode;
import com.studySrpingBoot.DeveloperMaker.Exception.DMakerExcepion;
import com.studySrpingBoot.DeveloperMaker.dto.CreateDeveloper;
import com.studySrpingBoot.DeveloperMaker.entity.Developer;
import com.studySrpingBoot.DeveloperMaker.repository.DeveloperRepository;
import com.studySrpingBoot.DeveloperMaker.type.DeveloperLevel;
import com.studySrpingBoot.DeveloperMaker.type.DeveloperSkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.nio.file.OpenOption;
import java.util.Optional;

@Service
@RequiredArgsConstructor //@Autowierd할 필요 없이, 생성자에 주입을 받는 방식. 생성자 자동생성)
public class DmakerService {
    //final 달아주면 @RequiredArgsConstructor 얘가 final 들어간 생성자를 자동생성해줌)
    //@Autowierd 할 필요없이 이렇게 많이 씀. 개별 테스트할 때 다른 클래스에 종속적이지 않기 위해
    private final DeveloperRepository developerRepository;

    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request){
        validateCreateDeveloperRequest(request);

        //트랜잭션 시작 business logic start
        Developer developer = Developer.builder()
                .developerLevel(request.getDeveloperLevel())
                .developerSkillType(request.getDeveloperSkillType())
                .experienceYear(request.getExperienceYears())
                .name(request.getName())
                .age(request.getAge())
                .memberId(request.getMemberId())
                .build();

        developerRepository.save(developer);
        return CreateDeveloper.Response.fromEntity(developer);

    }

    private void validateCreateDeveloperRequest
            (CreateDeveloper.Request request) {
        //business validation

        DeveloperLevel developerLevel = request.getDeveloperLevel();
        Integer experienceYears = request.getExperienceYears();
        if(developerLevel == DeveloperLevel.SENIOR
        && experienceYears < 10 ){
            //throw new RuntimeException("SENIOR need 10 years experience.");
            throw new DMakerExcepion(DMakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
        if(developerLevel == DeveloperLevel.JUNGIOR
        && (experienceYears < 4 || experienceYears > 10)){
            throw new DMakerExcepion(DMakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
        if(developerLevel == DeveloperLevel.JUNGIOR && experienceYears > 4 ){
            throw new DMakerExcepion(DMakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }

        //memberId가 동일한 값이 잇는지 repository에서 체크
        developerRepository.findByMemberId(request.getMemberId())
                .ifPresent(developer -> {
                    throw new DMakerExcepion(DMakerErrorCode.DUPLICATED_MEMBER_ID);
                });
        //java11부터는 위처럼 if문 사용할 수 있음
//              Optional<Developer> developer = developerRepository.findByMemberId(request.getMemberId())
//        if(developer.isPresent()){
//            throw new DMakerExcepion(DMakerErrorCode.DUPLICATED_MEMBER_ID);
//        }
    }

}
