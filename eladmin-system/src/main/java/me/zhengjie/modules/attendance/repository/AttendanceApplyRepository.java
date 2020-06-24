package me.zhengjie.modules.attendance.repository;

import me.zhengjie.modules.attendance.domain.AttendanceApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author zzy
* @date 2020-05-05
*/
public interface AttendanceApplyRepository extends JpaRepository<AttendanceApply, Long>, JpaSpecificationExecutor<AttendanceApply> {
}