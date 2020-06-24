package me.zhengjie.modules.recruitment.service.mapper;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.modules.recruitment.domain.RecruitmentJob;
import me.zhengjie.modules.recruitment.service.dto.RecruitmentJobDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author zzy
* @date 2020-05-20
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecruitmentJobMapper extends BaseMapper<RecruitmentJobDto, RecruitmentJob> {

}