package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;
import me.zhengjie.modules.quartz.domain.QuartzJob;

import java.sql.Date;
import java.util.List;

@Data
public class TaskDatabaseCriteria {
    @Query
    private Integer state;
    @Query
    private Long fromUserId;
    @Query
    private Long toUserId;
    @Query(type = Query.Type.BETWEEN)
    private List<Date> finishTime;
}

