package me.zhengjie.modules.system.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author zzy
* @date 2020-04-02
*/
@Entity
@Data
@Table(name="task")
public class Task implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "task_name")
    private String taskName;

    @Column(name="detail_content")
    private String detailContent;

    @Column(name = "from_user_id")
    private Long fromUserId;

    @Column(name = "to_user_id")
    private Long toUserId;

    /** 发布者姓名 */
    @Column(name = "from_user_name")
    private String fromUserName;

    /** 指定人的姓名 */
    @Column(name = "to_user_name")
    private String toUserName;


    /** 任务开始时间 */
    @Column(name = "start_time")
    private Timestamp startTime;

    /** 任务结束时间 */
    @Column(name = "finish_time")
    private Timestamp finishTime;

    /** 0 未完成；1已提交待打分；2完成 已打分；3 超时 */
    @Column(name = "state")
    private Integer state;

    @Column(name = "report_content")
    String reportContent;

    @Column(name = "score")
    Integer score;

    @Column(name = "report_time")
    Timestamp reportTime;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    private Timestamp updateTime;


    public void copy(Task source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}