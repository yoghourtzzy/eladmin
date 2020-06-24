package me.zhengjie.modules.performance.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
 * @author zzy
 * @date 2020-06-09
 */
@Entity
@Data
@Table(name="performance")
public class Performance implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "month")
    private Timestamp month;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name")
    private String name;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "dept_name")
    private String deptName;

    @Column(name = "job_id")
    private Long jobId;

    @Column(name = "job_name")
    private String jobName;

    @Column(name = "task_score")
    private Double taskScore;

    @Column(name = "monthly_score")
    private Double monthlyScore;

    @Column(name = "final_score")
    private Double finalScore;

    @Column(name = "comment")
    private String comment;

    @Column(name = "update_time")
    @UpdateTimestamp
    private Timestamp updateTime;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    @Column(name = "assessed_user_id")
    private Long assessedUserId;

    @Column(name = "assessed_user_name")
    private String assessedUserName;

    @Column(name = "dept_id")
    private Long deptId;

    public void copy(Performance source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
