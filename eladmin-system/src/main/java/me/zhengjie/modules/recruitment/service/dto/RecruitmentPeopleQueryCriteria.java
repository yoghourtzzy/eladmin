package me.zhengjie.modules.recruitment.service.dto;

import io.swagger.models.auth.In;
import lombok.Data;
import java.util.List;
import me.zhengjie.annotation.Query;

import javax.persistence.Column;

/**
* @author zzy
* @date 2020-06-08
*/
@Data
public class RecruitmentPeopleQueryCriteria{
    @Query
    private Integer process;
    @Query
    private Integer resumeState;
    @Query
    private Integer interviewState;
    @Query
    private Integer offerState;
    @Query
    private Long recruitmentJobId;
}
