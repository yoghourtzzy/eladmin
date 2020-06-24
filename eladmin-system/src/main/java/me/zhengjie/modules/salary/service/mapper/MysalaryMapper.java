package me.zhengjie.modules.salary.service.mapper;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.modules.salary.domain.Mysalary;
import me.zhengjie.modules.salary.service.dto.MysalaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author zzy
* @date 2020-06-17
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MysalaryMapper extends BaseMapper<MysalaryDto, Mysalary> {

}