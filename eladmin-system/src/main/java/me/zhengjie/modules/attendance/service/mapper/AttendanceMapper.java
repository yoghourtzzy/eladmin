package me.zhengjie.modules.attendance.service.mapper;

import me.zhengjie.base.BaseMapper;
import me.zhengjie.modules.attendance.domain.Attendance;
import me.zhengjie.modules.attendance.service.dto.AttendanceDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
* @author zzy
* @date 2020-04-26
*/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AttendanceMapper extends BaseMapper<AttendanceDto, Attendance> {

}