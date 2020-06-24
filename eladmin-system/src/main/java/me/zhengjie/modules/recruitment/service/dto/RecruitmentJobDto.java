package me.zhengjie.modules.recruitment.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author zzy
* @date 2020-05-20
*/
@Data
public class RecruitmentJobDto implements Serializable {

    private Long id;
    /** 关联岗位 */
    private String jobName;

    private String name;

    private Long jobId;
    //接收简历人数
    private Integer acceptResume;
    //入职人数
    private Integer acceptOffer;

    /** 0 全职 1劳务派遣 2实习 */
    private Integer jobType;

    private String deptName;

    private Long deptId;

    /** 0已完成 1未完成 */
    private Integer state;

    private Integer needNum;

    private Timestamp createTime;

    private Timestamp updateTime;


}
