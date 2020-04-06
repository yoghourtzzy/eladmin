package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.domain.Task;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.modules.system.repository.TaskRepository;
import me.zhengjie.modules.system.service.TaskService;
import me.zhengjie.modules.system.service.dto.TaskDto;
import me.zhengjie.modules.system.service.dto.TaskQueryCriteria;
import me.zhengjie.modules.system.service.mapper.TaskMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @author zzy
* @date 2020-04-02
*/
@Service
//@CacheConfig(cacheNames = "task")
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository, TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(TaskQueryCriteria criteria, Pageable pageable){
        Page<Task> page = taskRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(taskMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<TaskDto> queryAll(TaskQueryCriteria criteria){
        return taskMapper.toDto(taskRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public TaskDto findById(Long id) {
        Task task = taskRepository.findById(id).orElseGet(Task::new);
        ValidationUtil.isNull(task.getId(),"Task","id",id);
        return taskMapper.toDto(task);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public TaskDto create(Task resources) {
        return taskMapper.toDto(taskRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Task resources) {
        Task task = taskRepository.findById(resources.getId()).orElseGet(Task::new);
        ValidationUtil.isNull( task.getId(),"Task","id",resources.getId());
        task.copy(resources);
        taskRepository.save(task);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            taskRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<TaskDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (TaskDto task : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" fromUserId",  task.getFromUserId());
            map.put(" toUserId",  task.getToUserId());
            map.put("任务开始时间", task.getStartTime());
            map.put("任务结束时间", task.getFinishTime());
            map.put("0 未完成；1已提交待打分；2完成 已打分；3 超时", task.getState());
            map.put(" createTime",  task.getCreateTime());
            map.put("发布者姓名", task.getFromUserName());
            map.put("指定人的姓名", task.getToUserName());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}