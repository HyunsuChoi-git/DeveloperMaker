package com.studySrpingBoot.DeveloperMaker.dto;

import com.studySrpingBoot.DeveloperMaker.entity.Developer;
import com.studySrpingBoot.DeveloperMaker.type.DeveloperLevel;
import com.studySrpingBoot.DeveloperMaker.type.DeveloperSkillType;
import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class CreateDeveloper {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Request{

        @NonNull //데이터검증
        private DeveloperLevel developerLevel;
        @NonNull
        private DeveloperSkillType developerSkillType;
        @NonNull
        @Min(1)
        @Max(28)
        private Integer experienceYears;

        @NonNull
        @Size(min=3, max=50, message="memberId size must 3~50")
        private String memberId;
        @NonNull
        @Size(min=3, max=20, message="name size must 3~20")
        private String name;
        @Min(18)
        private Integer age;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Response{
        private DeveloperLevel developerLevel;
        private DeveloperSkillType developerSkillType;
        private Integer experienceYears;

        private String memberId;
        private String name;
        private Integer age;

        //Response dto를 만들 때는 developer생성 직후 바로 Entity를 만들어주기 때문에
        // Entity와 강한 결합을 보인다. 그때 아래처럼 fromEntity라는 메소드를 생성하여
        // 바로 developer Entity를 받아 Response를 만들어준다.
        public static Response fromEntity(Developer developer){
            return Response.builder()
                    .developerLevel(developer.getDeveloperLevel())
                    .developerSkillType(developer.getDeveloperSkillType())
                    .experienceYears(developer.getExperienceYear())
                    .memberId(developer.getMemberId())
                    .name(developer.getName())
                    .age(developer.getAge())
                    .build();
        }
    }
}
