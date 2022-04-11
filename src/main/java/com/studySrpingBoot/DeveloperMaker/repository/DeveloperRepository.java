package com.studySrpingBoot.DeveloperMaker.repository;

import com.studySrpingBoot.DeveloperMaker.entity.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//DB에 저장할 수 있는 기능 (JPA기술을 활용한)
// JpaRepository의 Developer, Long를 다 갖는 repository빈
@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    //JPA에서 제공하는 기능으로,
    //메소드명으로 특정 컬럼을 검색할 수 있다.  findByColumnName
    Optional<Developer> findByMemberId(String memberId);
}
