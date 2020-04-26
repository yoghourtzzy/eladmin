package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.Task;
import me.zhengjie.modules.system.service.dto.TaskDto;
import me.zhengjie.modules.system.service.dto.TaskQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author zzy
* @date 2020-04-02
*/
public interface TaskService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(TaskQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有分配给我的任务
     *  @param criteria 查询参数
     * @param pageable 分页参数
     * @return  Map<String,Object>
     */
    Map<String,Object> queryTaskToMe(TaskQueryCriteria criteria ,Pageable pageable);

    /**
     * 查询所有我分配的任务
     * @param criteria 查询参数
     * @param pageable 分页参数
     * @return  Map<String,Object>
     */
    Map<String,Object> queryTaskFromMe(TaskQueryCriteria criteria ,Pageable pageable);


    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<TaskDto>
    */
    List<TaskDto> queryAll(TaskQueryCriteria criteria);


    /**
     * 根据ID查询
     * @param id ID
     * @return TaskDto
     */
    TaskDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return TaskDto
    */
    TaskDto create(Task resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(Task resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
     * 单个删除
     * @param id /
     */
    void delete(Long id) throws Exception;

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<TaskDto> all, HttpServletResponse response) throws IOException;

    //以下为任务汇报相关内容

    /**
     * 汇报任务
     * @param resources
     * @return TaskDto
     */
    TaskDto report(Task resources) throws Exception;

    /**
     * 删除任务汇报
     * @param id 任务id
     * @throws Exception 任务已经被评审
     */
    void deleteReport(Long id) throws Exception;

    /**
     * 任务评分
     * @param resources
     * @return
     */
    TaskDto grade(Task resources) throws Exception;
}