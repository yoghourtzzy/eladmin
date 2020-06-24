package me.zhengjie.modules.recruitment.repository;

import me.zhengjie.modules.recruitment.domain.RecruitmentJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author zzy
* @date 2020-05-20
*/
public interface RecruitmentJobRepository extends JpaRepository<RecruitmentJob, Long>, JpaSpecificationExecutor<RecruitmentJob> {
}