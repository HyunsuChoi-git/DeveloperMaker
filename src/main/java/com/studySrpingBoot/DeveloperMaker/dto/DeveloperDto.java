package com.studySrpingBoot.DeveloperMaker.dto;

import com.studySrpingBoot.DeveloperMaker.entity.Developer;
import com.studySrpingBoot.DeveloperMaker.type.DeveloperLevel;
import com.studySrpingBoot.DeveloperMaker.type.DeveloperSkillType;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperDto {
    private DeveloperSkillType developerSkillType;
    private DeveloperLevel developerLevel;
    private String memberId;

    public static DeveloperDto fromEntity(Developer developer){
        return DeveloperDto.builder()
                .developerLevel(developer.getDeveloperLevel())
                .developerSkillType(developer.getDeveloperSkillType())
                .memberId(developer.getMemberId())
                .build();
    }
}
