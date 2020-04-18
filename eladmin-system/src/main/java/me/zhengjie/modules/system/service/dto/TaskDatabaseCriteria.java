package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;
import me.zhengjie.modules.quartz.domain.QuartzJob;

@Data
public class TaskDatabaseCriteria {
    @Query
    private Integer state;
    @Query
    private Long fromUserId;
    @Query
    private Long toUserId;
}

