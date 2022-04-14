package com.studySrpingBoot.DeveloperMaker.dto;

import com.studySrpingBoot.DeveloperMaker.code.StatusCode;
import com.studySrpingBoot.DeveloperMaker.entity.Developer;
import com.studySrpingBoot.DeveloperMaker.type.DeveloperLevel;
import com.studySrpingBoot.DeveloperMaker.type.DeveloperSkillType;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperDetailDto {
    private DeveloperLevel developerLevel;
    private DeveloperSkillType developerSkillType;
    private Integer experienceYears;

    private String memberId;
    private String name;
    private Integer age;

    private StatusCode statusCode;
    //Response dto를 만들 때는 developer생성 직후 바로 Entity를 만들어주기 때문에
    // Entity와 강한 결합을 보인다. 그때 아래처럼 fromEntity라는 메소드를 생성하여
    // 바로 developer Entity를 받아 Response를 만들어준다.
    public static DeveloperDetailDto fromEntity(Developer developer){
        return DeveloperDetailDto.builder()
                .developerLevel(developer.getDeveloperLevel())
                .developerSkillType(developer.getDeveloperSkillType())
                .experienceYears(developer.getExperienceYear())
                .memberId(developer.getMemberId())
                .name(developer.getName())
                .age(developer.getAge())
                .statusCode(developer.getStatusCode())
                .build();
    }
}
