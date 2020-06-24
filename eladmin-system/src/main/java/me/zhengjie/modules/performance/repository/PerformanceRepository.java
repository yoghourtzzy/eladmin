package me.zhengjie.modules.performance.repository;

import me.zhengjie.modules.performance.domain.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
* @author zzy
* @date 2020-06-09
*/
public interface PerformanceRepository extends JpaRepository<Performance, Long>, JpaSpecificationExecutor<Performance> {
  //  List<Performance> saveAll(List<Performance> performances);
}
