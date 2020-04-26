package me.zhengjie.modules.attendance.repository;

import me.zhengjie.modules.attendance.domain.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.sql.Date;

/**
* @author zzy
* @date 2020-04-26
*/
public interface AttendanceRepository extends JpaRepository<Attendance, Long>, JpaSpecificationExecutor<Attendance> {
    Attendance findByRecDate(Date date);
}