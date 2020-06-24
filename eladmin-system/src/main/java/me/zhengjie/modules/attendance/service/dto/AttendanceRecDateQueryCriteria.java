package me.zhengjie.modules.attendance.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

/**
* @author zzy
* @date 2020-04-26
*/
@Data
public class AttendanceRecDateQueryCriteria{
    @Query
   private Long userId;
    @Query
    private Date recDate;
}
