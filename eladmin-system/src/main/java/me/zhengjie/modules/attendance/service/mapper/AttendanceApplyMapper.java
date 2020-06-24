package me.zhengjie.modules.attendance.service.mapper;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.modules.attendance.domain.AttendanceApply;
import me.zhengjie.modules.attendance.service.dto.AttendanceApplyDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author zzy
* @date 2020-05-05
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AttendanceApplyMapper extends BaseMapper<AttendanceApplyDto, AttendanceApply> {
}