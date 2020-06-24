package me.zhengjie.modules.recruitment.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author zzy
* @date 2020-05-20
*/
@Entity
@Data
@Table(name="recruitment_job")
public class RecruitmentJob implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    /** 关联岗位 */
    @Column(name = "job_name")
    private String jobName;


    @Column(name = "job_id")
    private Long jobId;

    /** 0 全职 1劳务派遣 2实习 */
    @Column(name = "job_type")
    private Integer jobType;

    @Column(name = "dept_name")
    private String deptName;

    @Column(name = "dept_id")
    private Long deptId;

    /** 0已完成 1未完成 */
    @Column(name = "state",nullable = false)
    private Integer state;

    @Column(name = "need_num")
    private Integer needNum;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;
    @UpdateTimestamp
    @Column(name = "update_time")
    private Timestamp updateTime;

    public void copy(RecruitmentJob source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
