package me.zhengjie.modules.attendance.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author zzy
* @date 2020-05-05
*/
@Entity
@Data
@Table(name="attendance_apply")
public class AttendanceApply implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 申请人姓名 */
    @Column(name = "from_user_name")
    private String fromUserName;

    @Column(name = "from_user_id")
    private Long fromUserId;

    @Column(name = "to_user_name")
    private String toUserName;

    @Column(name = "to_user_id")
    private Long toUserId;

    /** 开始时间 */
    @Column(name = "start_date")
    private Date startDate;

    /** 结束时间 */
    @Column(name = "finish_date")
    private Date finishDate;


    /** 申请原因 */
    @Column(name = "reason")
    private String reason;

    /** 0请假  1出差 */
    @Column(name = "type")
    private Integer type;

    /** 0未审批 1审批同意 2审批不同意 */
    @Column(name = "state")
    private Integer state;

    @Column(name = "approve_time")
    private Timestamp approveTime;

    /** 审批意见/备注 */
    @Column(name = "comment")
    private String comment;

    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    @UpdateTimestamp
    @Column(name = "update_time")
    private Timestamp updateTime;

    public void copy(AttendanceApply source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}