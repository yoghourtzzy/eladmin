package me.zhengjie.modules.system.rest;


import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.system.domain.Task;
import me.zhengjie.modules.system.service.TaskService;
import me.zhengjie.modules.system.service.dto.TaskMonthlyAveScoreDto;
import me.zhengjie.modules.system.service.dto.TaskQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.sql.Date;
import javax.servlet.http.HttpServletResponse;

/**
* @author zzy
* @date 2020-04-02
*/
@Api(tags = "api/task管理")
@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('task:list')")
    public void download(HttpServletResponse response, TaskQueryCriteria criteria) throws IOException {
        taskService.download(taskService.queryAll(criteria), response);
    }

//    @GetMapping
//    @Log("查询api/task")
//    @ApiOperation("查询api/task")
//    @PreAuthorize("@el.check('task:list')")
//    public ResponseEntity<Object> getTasks(TaskQueryCriteria criteria, Pageable pageable){
//        return new ResponseEntity<>(taskService.queryAll(criteria,pageable),HttpStatus.OK);
//    }

    @GetMapping("/tome")
    @Log("查询api/task/tome")
    @ApiOperation("查询api/task/tome")
    @PreAuthorize("@el.check('task:tome')")
    public ResponseEntity<Object> getTasksToMe(TaskQueryCriteria criteria,Pageable pageable){
        return new ResponseEntity<>(taskService.queryTaskToMe(criteria,pageable),HttpStatus.OK);
    }

    @GetMapping("/fromme")
    @Log("查询api/task/fromme")
    @ApiOperation("查询api/task/fromme")
    @PreAuthorize("@el.check('task:fromme')")
    public ResponseEntity<Object> getTaskFromMe(TaskQueryCriteria criteria,Pageable pageable){
        return new ResponseEntity<>(taskService.queryTaskFromMe(criteria,pageable),HttpStatus.OK);
    }


    @PostMapping
    @Log("新增api/task")
    @ApiOperation("新增api/task")
    @PreAuthorize("@el.check('task:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Task resources){
        return new ResponseEntity<>(taskService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/task")
    @ApiOperation("修改api/task")
    @PreAuthorize("@el.check('task:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Task resources){
        taskService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除api/task")
    @ApiOperation("删除api/task")
    @PreAuthorize("@el.check('task:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long id) throws Exception {
        taskService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Log("新增api/task/report")
    @ApiOperation("新增api/task/report")
    @PreAuthorize("@el.check('taskreport:add')")
    @PostMapping("/report")
    public ResponseEntity<Object> getTasksToMe(@Validated @RequestBody Task resources) throws Exception {
        return new ResponseEntity<>(taskService.report(resources),HttpStatus.CREATED);
    }



    @Log("删除api/task/report")
    @ApiOperation("删除api/task/report")
    @PreAuthorize("@el.check('taskreport:del')")
    @DeleteMapping("/report")
    public ResponseEntity<Object> deleteReport(@RequestBody Long id) throws Exception {
        taskService.deleteReport(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 任务评分接口
     * @param
     * @return
     * @throws Exception
     */
    @Log("修改api/task/grade")
    @ApiOperation("修改api/task/grade")
    @PreAuthorize("@el.check('task:grade')")
    @PutMapping("/grade")
    public ResponseEntity<Object> grade(@RequestBody Task resources) throws Exception {
        return new ResponseEntity<>( taskService.grade(resources),HttpStatus.OK);
    }
    @Log("均分api/task/ave")
    @ApiOperation("均分/task/ave")
    @GetMapping("/ave")
    public ResponseEntity<Object> getMonthLyAveScore(TaskMonthlyAveScoreDto dto) throws Exception {
        return new ResponseEntity<>( taskService.monthlyAveScore(dto.getUserid(),dto.getMonth()),HttpStatus.OK);
    }

}
