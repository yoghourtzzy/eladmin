package me.zzy.wechat.service.impl;


import me.zhengjie.utils.*;
import me.zzy.wechat.domain.Task;
import me.zzy.wechat.repository.TaskRepository;
import me.zzy.wechat.service.TaskService;
import me.zzy.wechat.service.dto.TaskDatabaseCriteria;
import me.zzy.wechat.service.dto.TaskDto;
import me.zzy.wechat.service.dto.TaskQueryCriteria;
import me.zzy.wechat.service.mapper.TaskMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;

/**
* @author zzy
* @date 2020-04-02
*/
@Service
//@CacheConfig(cacheNames = "task")
public class TaskServiceImpl implements TaskService {

    @Resource
    private  TaskRepository taskRepository;
    @Resource
    private  TaskMapper taskMapper;

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(TaskQueryCriteria criteria, Pageable pageable){
        Page<Task> page = taskRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(taskMapper::toDto));
    }

    @Override
    public Map<String, Object> queryTaskToMe(TaskQueryCriteria criteria,Pageable pageable) {
        TaskDatabaseCriteria databaseCriteria = new TaskDatabaseCriteria();
        databaseCriteria.setToUserId(3L);
        databaseCriteria.setState(criteria.getState());
        Page<Task> page = taskRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, databaseCriteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(taskMapper::toDto));
    }

    @Override
    public Map<String, Object> queryTaskFromMe(TaskQueryCriteria criteria, Pageable pageable) {
        TaskDatabaseCriteria databaseCriteria = new TaskDatabaseCriteria();
        databaseCriteria.setFromUserId(3L);
        databaseCriteria.setState(criteria.getState());
        Page<Task> page = taskRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, databaseCriteria, criteriaBuilder), pageable);
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
        resources.setFromUserName("test");
        resources.setFromUserId(3L);
        resources.setState(0);
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
    public void delete(Long id) throws Exception {
        Task task=taskRepository.getOne(id);
        if(task.getState()==1){
            throw  new Exception("任务已经汇报，无法删除");
        }
        taskRepository.deleteById(id);
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

    /**
     * 汇报任务
     * @param resources 汇报内容
     * @return
     */
    @Override
    public TaskDto report(Task resources) throws Exception {
        Task task=taskRepository.getOne(resources.getId());
        //汇报时任务已经被删除
        if(task==null){
            throw new Exception("任务已被删除，请刷新");
        }
        task.setReportContent(resources.getReportContent());
        task.setReportTime(new Timestamp(System.currentTimeMillis()));
          task.setState(1);
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    public void deleteReport(Long id) throws Exception{
        Task task=taskRepository.getOne(id);
        //有一种情况就是点击删除汇报的时候任务已经被评审了，但是前端没有更新
        if(task.getState()==2){
            throw new Exception("任务已经评审,请刷新");
        }
        task.setReportContent(null);
        task.setReportTime(null);
        task.setState(0);
        taskRepository.save(task);
    }

    @Override
    public TaskDto grade(Task resources) throws Exception {
        //有一种情况就是点击评分的时候任务已经删除汇报了，但是前端没有更新
        Task task=taskRepository.getOne(resources.getId());
        if(task.getState()==0){
            throw new Exception("任务已经撤回汇报,请刷新");
        }
        task.setScore(resources.getScore());
        task.setState(2);
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    public Double monthlyAveScore(Long userid,Timestamp month) {
        TaskDatabaseCriteria queryCriteria=new TaskDatabaseCriteria();
        queryCriteria.setToUserId(userid);
        LocalDate oneDay=month.toLocalDateTime().toLocalDate();
        LocalDate firstDay = oneDay.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastDay = oneDay.with(TemporalAdjusters.lastDayOfMonth());
        List<Date> dateScale=new ArrayList<>();
        dateScale.add(Date.valueOf(firstDay));
        dateScale.add(Date.valueOf(lastDay));
        queryCriteria.setFinishTime(dateScale);
        queryCriteria.setState(2);
        List<Task> taskList=taskRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,queryCriteria,criteriaBuilder));
        List<Integer> taskScoreList=taskList.stream().map(Task::getScore).collect(Collectors.toList());
        if(taskList.isEmpty()){
            return 0.0;
        }else{
            Integer sum= taskScoreList.stream().mapToInt((x)->x).sum();
            return (double) sum / taskList.size();
        }
    }
}
