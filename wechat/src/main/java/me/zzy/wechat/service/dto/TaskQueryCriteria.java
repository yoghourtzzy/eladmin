package me.zzy.wechat.service.dto;

import lombok.Data;
import me.zhengjie.annotation.Query;

/**
* @author zzy
* @date 2020-04-02
*/
@Data
public class TaskQueryCriteria {
    @Query
    private Integer state;
}
