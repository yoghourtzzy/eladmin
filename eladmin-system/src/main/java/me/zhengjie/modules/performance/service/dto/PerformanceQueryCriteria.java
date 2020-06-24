package me.zhengjie.modules.performance.service.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;
import me.zhengjie.annotation.Query;

/**
* @author zzy
* @date 2020-06-09
*/
@Data
public class PerformanceQueryCriteria{
    @Query
    private Timestamp month;
    @Query
    private Long assessedUserId;
    @Query
    private Long userId;
}
