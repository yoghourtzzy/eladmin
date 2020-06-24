package me.zhengjie.modules.recruitment.service.mapper;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.modules.recruitment.domain.RecruitmentPeople;
import me.zhengjie.modules.recruitment.service.dto.RecruitmentPeopleDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author zzy
* @date 2020-06-08
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RecruitmentPeopleMapper extends BaseMapper<RecruitmentPeopleDto, RecruitmentPeople> {

}