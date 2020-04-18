package me.zhengjie.modules.system.service.dto;

import lombok.Data;

import javax.persistence.Column;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author zzy
* @date 2020-04-02
*/
@Data
public class TaskDto implements Serializable {

    private Long id;

    private String taskName;
    private String detailContent;

    private Long fromUserId;

    private Long toUserId;

    /** 发布者姓名 */
    private String fromUserName;

    /** 指定人的姓名 */
    private String toUserName;

    /** 任务开始时间 */
    private Timestamp startTime;

    /** 任务结束时间 */
    private Timestamp finishTime;

    /** 0 未完成；1已提交待打分；2完成 已打分；3 超时 */
    private Integer state;

    private Timestamp createTime;

    String reportContent;

    Integer score;

    Timestamp reportTime;
}