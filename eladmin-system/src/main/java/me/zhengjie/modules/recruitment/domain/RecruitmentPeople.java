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
* @date 2020-06-08
*/
@Entity
@Data
@Table(name="recruitment_people")
public class RecruitmentPeople implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "age")
    private Integer age;

    @Column(name = "sex")
    private String sex;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    /** 毕业学校 */
    @Column(name = "school")
    private String school;

    @Column(name = "recruitment_job_id")
    private Long recruitmentJobId;

    @Column(name = "recruitment_job_name")
    private String recruitmentJobName;


    @Column(name = "resume_file_id")
    private Long resumeFileId;

    @Column(name = "process")
    private Integer process;

    /** 简历状态/0带筛选/1淘汰/2通过 */
    @Column(name = "resume_state")
    private Integer resumeState;

    /** 面试状态/0未进面/1 1面/2 2面/3 3面 /4 淘汰 */
    @Column(name = "interview_state")
    private Integer interviewState;

    /** 0未发送  1未回复  1同意 2不同意 */
    @Column(name = "offer_state")
    private Integer offerState;
    @CreationTimestamp
    @Column(name = "create_time")
    private Timestamp createTime;

    @UpdateTimestamp
    @Column(name = "update_time")
    private Timestamp updateTime;
    @Column(name = "type")
    private String type;
    @Column(name = "realName")
    private String realName;

    public void copy(RecruitmentPeople source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
