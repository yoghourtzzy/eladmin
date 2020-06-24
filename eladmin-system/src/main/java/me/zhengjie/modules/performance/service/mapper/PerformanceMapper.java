package me.zhengjie.modules.performance.service.mapper;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.modules.performance.domain.Performance;
import me.zhengjie.modules.performance.service.dto.PerformanceDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author zzy
* @date 2020-06-09
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PerformanceMapper extends BaseMapper<PerformanceDto, Performance> {

}