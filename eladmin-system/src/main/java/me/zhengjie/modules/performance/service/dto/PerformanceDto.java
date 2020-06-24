package me.zhengjie.modules.performance.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
 * @author zzy
 * @date 2020-06-09
 */
@Data
public class PerformanceDto implements Serializable {

    private Long id;

    private Timestamp month;

    private Long userId;

    private String name;

    private String userName;

    private String deptName;

    private Long jobId;

    private String jobName;

    private Double taskScore;

    private Double monthlyScore;

    private Double finalScore;

    private String comment;

    private Timestamp updateTime;

    private Timestamp createTime;

    private Long assessedUserId;

    private String assessedUserName;

    private Long deptId;
}
