package me.zhengjie.modules.attendance.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;

import java.sql.Date;
import java.util.List;

@Data
public class AttendanceDateScaleQueryCriteria {
    private Long userId;
    // BETWEEN
    @Query(type = Query.Type.BETWEEN)
    private List<Date> recDate;
}
