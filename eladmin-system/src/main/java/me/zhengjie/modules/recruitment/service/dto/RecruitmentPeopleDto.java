package me.zhengjie.modules.recruitment.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author zzy
* @date 2020-06-08
*/
@Data
public class RecruitmentPeopleDto implements Serializable {

    private Long id;

    private String name;

    private Integer age;

    private String sex;

    private String email;

    private String phone;

    /** 毕业学校 */
    private String school;

    private Long recruitmentJobId;

    private String recruitmentJobName;


    private Long resumeFileId;

    private Integer process;

    /** 简历状态/0带筛选/1淘汰/2通过 */
    private Integer resumeState;

    /** 面试状态/0未进面/1 1面/2 2面/3 3面 /4 淘汰 */
    private Integer interviewState;

    /** 0未发送  1未回复  1同意 2不同意 */
    private Integer offerState;

    private Timestamp createTime;

    private Timestamp updateTime;
    private String type;
    private String realName;
}
