package me.zhengjie.modules.attendance.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.attendance.domain.Attendance;
import me.zhengjie.modules.attendance.service.AttendanceService;
import me.zhengjie.modules.attendance.service.dto.AttendanceQueryCriteria;
import org.springframework.data.domain.Pageable;
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
* @date 2020-04-26
*/
@Api(tags = "api/attendance管理")
@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('attendance:list')")
    public void download(HttpServletResponse response, AttendanceQueryCriteria criteria) throws IOException {
        attendanceService.download(attendanceService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/attendance")
    @ApiOperation("查询api/attendance")
    @PreAuthorize("@el.check('attendance:list')")
    public ResponseEntity<Object> getAttendances(AttendanceQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(attendanceService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增api/attendance")
    @ApiOperation("新增api/attendance")
    @PreAuthorize("@el.check('attendance:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Attendance resources){
        return new ResponseEntity<>(attendanceService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/attendance")
    @ApiOperation("修改api/attendance")
    @PreAuthorize("@el.check('attendance:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Attendance resources){
        attendanceService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除api/attendance")
    @ApiOperation("删除api/attendance")
    @PreAuthorize("@el.check('attendance:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        attendanceService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping
    @Log("api/attendance/checkin")
    @ApiOperation("api/attendance/clockin")
    @PreAuthorize("@el.check('attendance:checkin')")
    public ResponseEntity<Object> clockin(){
        return new ResponseEntity<>(attendanceService.checkin(),HttpStatus.CREATED);
    }
}