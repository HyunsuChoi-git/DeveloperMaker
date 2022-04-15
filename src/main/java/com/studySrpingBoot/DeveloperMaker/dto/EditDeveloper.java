package com.studySrpingBoot.DeveloperMaker.dto;

import com.studySrpingBoot.DeveloperMaker.entity.Developer;
import com.studySrpingBoot.DeveloperMaker.type.DeveloperLevel;
import com.studySrpingBoot.DeveloperMaker.type.DeveloperSkillType;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class EditDeveloper {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class Request{

        @NonNull //데이터검증
        private DeveloperLevel developerLevel;
        @NonNull
        private DeveloperSkillType developerSkillType;
        @NonNull
        @Min(1)
        @Max(28)
        private Integer experienceYears;

    }
}
