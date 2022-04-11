package com.studySrpingBoot.DeveloperMaker.service;

import com.studySrpingBoot.DeveloperMaker.entity.Developer;
import com.studySrpingBoot.DeveloperMaker.repository.DeveloperRepository;
import com.studySrpingBoot.DeveloperMaker.type.DeveloperLevel;
import com.studySrpingBoot.DeveloperMaker.type.DeveloperSkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor //@Autowierd할 필요 없이, 생성자에 주입을 받는 방식. 생성자 자동생성)
public class DmakerService {

    //final 달아주면 @RequiredArgsConstructor 얘가 final 들어간 생성자를 자동생성해줌)
    //@Autowierd 할 필요없이 이렇게 많이 씀. 개별 테스트할 때 다른 클래스에 종속적이지 않기 위해
    private final DeveloperRepository developerRepository;

    @Transactional
    public void createDeveloper(){

            //트랜잭션 시작 business logic start
            Developer developer = Developer.builder()
                    .developerLevel(DeveloperLevel.JUNIOR)
                    .developerSkillType(DeveloperSkillType.FRONT_END)
                    .experienceYear(1)
                    .name("Olaf")
                    .age(5)
                    .build();

            //A계좌에서 1만원 출금, B계좌에 1만원 입금
            developerRepository.save(developer);
            developerRepository.delete(developer1);

        }
    }

}
