package com.studySrpingBoot.DeveloperMaker.entity;

import com.studySrpingBoot.DeveloperMaker.code.StatusCode;
import com.studySrpingBoot.DeveloperMaker.type.DeveloperLevel;
import com.studySrpingBoot.DeveloperMaker.type.DeveloperSkillType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Enumerated(EnumType.STRING)
    private DeveloperLevel developerLevel;

    @Enumerated(EnumType.STRING)
    private DeveloperSkillType developerSkillType;

    private Integer experienceYear;
    private String memberId;
    private String name;
    private Integer age;

    @Enumerated(EnumType.STRING)
    private StatusCode statusCode;
   //private String findDevelopersStatusCode;
    //jpa기능중, 자동적으로 값을 세팅해주는 오디팅 기능.
    //생성시점, 수정시점을 자동으로 세팅해줌
    // main 클래스에 @EnableJpaAuditing 추가
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

}
