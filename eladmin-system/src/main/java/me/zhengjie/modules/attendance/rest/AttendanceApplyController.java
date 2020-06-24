package me.zhengjie.modules.attendance.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.attendance.domain.AttendanceApply;
import me.zhengjie.modules.attendance.service.AttendanceApplyService;
import me.zhengjie.modules.attendance.service.dto.AttendanceApplyQueryCriteria;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.utils.SecurityUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author zzy
* @date 2020-05-05
*/
@Api(tags = "api/attendanceApply管理")
@RestController
@RequestMapping("/api/attendanceApply")
public class AttendanceApplyController {

    private final AttendanceApplyService attendanceApplyService;

    public AttendanceApplyController(AttendanceApplyService attendanceApplyService) {
        this.attendanceApplyService = attendanceApplyService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('attendanceApply:list')")
    public void download(HttpServletResponse response, AttendanceApplyQueryCriteria criteria) throws IOException {
        attendanceApplyService.download(attendanceApplyService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/attendanceApply")
    @ApiOperation("查询api/attendanceApply")
    @PreAuthorize("@el.check('attendanceApply:list')")
    public ResponseEntity<Object> getAttendanceApplys(AttendanceApplyQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(attendanceApplyService.queryAll(criteria,pageable),HttpStatus.OK);
    }


    @GetMapping("/myapply")
    @Log("查询 api/attendanceApply/myapply")
    @ApiOperation("查询api/attendanceApply/ ")
    @PreAuthorize("@el.check('attendanceApply:myapply')")
    public ResponseEntity<Object> getMyAttendanceApplys(AttendanceApplyQueryCriteria criteria,@PageableDefault(sort = "id",direction = Sort.Direction.DESC)Pageable pageable){
        return new ResponseEntity<>(attendanceApplyService.queryMyApplys(criteria,pageable),HttpStatus.OK);
    }

    @GetMapping("/myapproval")
    @Log("查询 api/attendanceApply/myapproval")
    @ApiOperation("查询api/attendanceApply/myapproval ")
    @PreAuthorize("@el.check('attendanceApply:myapproval')")
    public ResponseEntity<Object> getMyAttendanceApprovals(AttendanceApplyQueryCriteria criteria,@PageableDefault(sort = "id",direction = Sort.Direction.DESC)Pageable pageable){
        return new ResponseEntity<>(attendanceApplyService.queryMyApprovals(criteria,pageable),HttpStatus.OK);
    }





    @PostMapping
    @Log("新增api/attendanceApply")
    @ApiOperation("新增api/attendanceApply")
    @PreAuthorize("@el.check('attendanceApply:add')")
    public ResponseEntity<Object> create(@RequestBody AttendanceApply resources){
        return new ResponseEntity<>(attendanceApplyService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/attendanceApply")
    @ApiOperation("修改api/attendanceApply")
    @PreAuthorize("@el.check('attendanceApply:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody AttendanceApply resources){
        attendanceApplyService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("approve")
    @Log("修改api/attendanceApply/approve")
    @ApiOperation("修改api/attendanceApply/approve")
    @PreAuthorize("@el.check('attendanceApply:approve')")
    public ResponseEntity<Object> approve(@Validated @RequestBody AttendanceApply resources) throws Exception {
        attendanceApplyService.approve(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除api/attendanceApply")
    @ApiOperation("删除api/attendanceApply")
    @PreAuthorize("@el.check('attendanceApply:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        attendanceApplyService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}