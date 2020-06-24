package me.zhengjie.modules.attendance.service.dto;

import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author zzy
* @date 2020-05-05
*/
@Data
public class AttendanceApplyDto implements Serializable {

    private Long id;

    /** 申请人姓名 */
    private String fromUserName;

    private Long fromUserId;

    private String toUserName;

    private Long toUserId;


    /** 开始时间 */
    private Date startDate;

    /** 结束时间 */
    private Date finishDate;

    /** 申请原因 */
    private String reason;

    /** 0请假  1出差 */
    private Integer type;

    /** 0未审批 1审批同意 2审批不同意 */
    private Integer state;

    private Timestamp approveTime;

    /** 审批意见/备注 */
    private String comment;

    private Timestamp createTime;

    private Timestamp updateTime;
}