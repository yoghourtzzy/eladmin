package me.zhengjie.modules.attendance.service.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import me.zhengjie.annotation.Query;

/**
* @author zzy
* @date 2020-05-05
*/
@Data
public class AttendanceApplyQueryCriteria{
    @Query
    private Long fromUserId;
    @Query
    private Long toUserId;
    @Query
    private Integer state;
    @Query
    private Integer type;
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}