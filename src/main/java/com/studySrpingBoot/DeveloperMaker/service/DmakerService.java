package com.studySrpingBoot.DeveloperMaker.service;

import com.studySrpingBoot.DeveloperMaker.Exception.DMakerErrorCode;
import com.studySrpingBoot.DeveloperMaker.Exception.DMakerExcepion;
import com.studySrpingBoot.DeveloperMaker.code.StatusCode;
import com.studySrpingBoot.DeveloperMaker.dto.CreateDeveloper;
import com.studySrpingBoot.DeveloperMaker.dto.DeveloperDetailDto;
import com.studySrpingBoot.DeveloperMaker.dto.DeveloperDto;
import com.studySrpingBoot.DeveloperMaker.dto.EditDeveloper;
import com.studySrpingBoot.DeveloperMaker.entity.Developer;
import com.studySrpingBoot.DeveloperMaker.entity.RetiredDeveloper;
import com.studySrpingBoot.DeveloperMaker.repository.DeveloperRepository;
import com.studySrpingBoot.DeveloperMaker.repository.RetiredDeveloperRepository;
import com.studySrpingBoot.DeveloperMaker.type.DeveloperLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //@Autowierd할 필요 없이, 생성자에 주입을 받는 방식. 생성자 자동생성)
public class DmakerService {
    //final 달아주면 @RequiredArgsConstructor 얘가 final 들어간 생성자를 자동생성해줌)
    //@Autowierd 할 필요없이 이렇게 많이 씀. 개별 테스트할 때 다른 클래스에 종속적이지 않기 위해
    private final DeveloperRepository developerRepository;
    private final RetiredDeveloperRepository retiredDeveloperRepository;

    public List<DeveloperDto> getAllEmployedDevelopers() {
        return developerRepository.findDevelopersByStatusCodeEquals(StatusCode.EMPLOYED)
                .stream().map(DeveloperDto::fromEntity)
                .collect(Collectors.toList());
    }

    public DeveloperDetailDto getDeveloper(String memberId) {
        return developerRepository.findByMemberId(memberId)
                .map(DeveloperDetailDto::fromEntity)
                .orElseThrow(()-> new DMakerExcepion(DMakerErrorCode.NO_DEVELOPER) );

    }

    @Transactional //변경되기전/후 트랜잭션처리
    public DeveloperDetailDto editDeveloper(String memberId, EditDeveloper.Request request) {
        validateEditDeveloperRequest(request, memberId);

        //memberId가 동일한 값이 잇는지 repository에서 체크
        Developer developer = developerRepository.findByMemberId(memberId)
                .orElseThrow(()-> new DMakerExcepion(DMakerErrorCode.NO_DEVELOPER));

        developer.setDeveloperLevel(request.getDeveloperLevel());
        developer.setDeveloperSkillType(request.getDeveloperSkillType());
        developer.setExperienceYear(request.getExperienceYears());
        developerRepository.save(developer);

;       return DeveloperDetailDto.fromEntity(developer);
    }


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
                .statusCode(StatusCode.EMPLOYED)
                .build();

        developerRepository.save(developer);
        return CreateDeveloper.Response.fromEntity(developer);

    }

    private void validateCreateDeveloperRequest
            (CreateDeveloper.Request request) {
        //business validation
        validateDeveloper(request.getDeveloperLevel(), request.getExperienceYears());

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

    private void validateEditDeveloperRequest(EditDeveloper.Request request, String memberId) {
        //business validation
        validateDeveloper(request.getDeveloperLevel(), request.getExperienceYears());

    }

    //validate 중복코드 함수화
    private void validateDeveloper(DeveloperLevel developerLevel, Integer developerYears){

        if(developerLevel == DeveloperLevel.SENIOR
                && developerYears < 10 ){
            //throw new RuntimeException("SENIOR need 10 years experience.");
            throw new DMakerExcepion(DMakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
        if(developerLevel == DeveloperLevel.JUNGIOR
                && (developerYears < 4 || developerYears > 10)){
            throw new DMakerExcepion(DMakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
        if(developerLevel == DeveloperLevel.JUNGIOR && developerYears > 4 ){
            throw new DMakerExcepion(DMakerErrorCode.LEVEL_EXPERIENCE_YEARS_NOT_MATCHED);
        }
    }

    @Transactional
    public DeveloperDetailDto deleteCeveloper(String memberId) {
        // 1. EMPLORED -> RETIRED
        // 2. save into RetiredDeveloper

        // ID 체크
        Developer developer = developerRepository.findByMemberId(memberId)
                .orElseThrow(()-> new DMakerExcepion(DMakerErrorCode.NO_DEVELOPER));
        // 1. EMPLORED -> RETIRED
        developer.setStatusCode(StatusCode.RETIRED);
        // 2. save into RetiredDeveloper
        RetiredDeveloper retiredDeveloper = RetiredDeveloper.builder()
                .memberId(memberId)
                .name(developer.getName())
                .build();
        retiredDeveloperRepository.save(retiredDeveloper);
        return DeveloperDetailDto.fromEntity(developer);
    }
}
