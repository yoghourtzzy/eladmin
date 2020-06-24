package me.zhengjie.modules.attendance.service;

import me.zhengjie.EladminSystemApplicationTests;
import me.zhengjie.config.WebSocketConfig;
import me.zhengjie.modules.attendance.domain.Attendance;
import me.zhengjie.modules.attendance.domain.AttendanceApply;
import me.zhengjie.modules.attendance.repository.AttendanceApplyRepository;
import me.zhengjie.modules.attendance.repository.AttendanceRepository;
import me.zhengjie.modules.security.config.SecurityConfig;
import me.zhengjie.modules.system.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@EnableAutoConfiguration (exclude=SecurityAutoConfiguration. class )
//@ComponentScan(basePackages = "me.zhengjie.modules.attendance")
//@ActiveProfiles({ "test" })
public class AttendanceApplySrviceTest {
    @Resource
    AttendanceApplyRepository attendanceApplyRepository;
    @Test
    public void createTest(){
       for(int i=0;i<20;++i){
           AttendanceApply attendanceApply=new AttendanceApply();
           attendanceApply.setToUserId(4L);//审批人全部设成zzy
           LocalDate startDate=LocalDate.now();
           LocalDate finishDate=startDate.plusDays(1);
           attendanceApply.setStartDate(Date.valueOf(startDate));
           attendanceApply.setFinishDate(Date.valueOf(finishDate));
           attendanceApply.setType(0);

           //currentUser为test
           attendanceApply.setFromUserId(3L);
           attendanceApply.setFromUserName("test");


           attendanceApply.setToUserName("zzy");
           attendanceApply.setState(0);
           attendanceApplyRepository.save(attendanceApply);
       }
    }

}
