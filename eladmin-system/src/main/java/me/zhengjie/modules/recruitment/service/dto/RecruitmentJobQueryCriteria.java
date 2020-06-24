package me.zhengjie.modules.recruitment.service.dto;

import lombok.Data;
import java.util.List;
import me.zhengjie.annotation.Query;

/**
* @author zzy
* @date 2020-05-20
*/
@Data
public class RecruitmentJobQueryCriteria{
    @Query
    Integer state;
    @Query
    Integer id;
}
