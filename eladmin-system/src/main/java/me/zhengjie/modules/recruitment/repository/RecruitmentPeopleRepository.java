package me.zhengjie.modules.recruitment.repository;

import me.zhengjie.modules.recruitment.domain.RecruitmentPeople;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author zzy
* @date 2020-06-08
*/
public interface RecruitmentPeopleRepository extends JpaRepository<RecruitmentPeople, Long>, JpaSpecificationExecutor<RecruitmentPeople> {
}