package me.zzy.wechat.service.mapper;

import me.zhengjie.base.BaseMapper;
import me.zzy.wechat.domain.Task;
import me.zzy.wechat.service.dto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

/**
* @author zzy
* @date 2020-04-02
*/

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper extends BaseMapper<TaskDto, Task> {

}
