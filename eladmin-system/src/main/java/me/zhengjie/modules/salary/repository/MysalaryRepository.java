package me.zhengjie.modules.salary.repository;

import me.zhengjie.modules.salary.domain.Mysalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author zzy
* @date 2020-06-17
*/
public interface MysalaryRepository extends JpaRepository<Mysalary, Long>, JpaSpecificationExecutor<Mysalary> {
}