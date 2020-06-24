package me.zhengjie.modules.attendance.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.attendance.domain.Attendance;
import me.zhengjie.modules.attendance.service.AttendanceService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
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

//    @Log("导出数据")
//    @ApiOperation("导出数据")
//    @GetMapping(value = "/download")
//    @PreAuthorize("@el.check('attendance:list')")
//    public void download(HttpServletResponse response, AttendanceQueryCriteria criteria) throws IOException {
//        attendanceService.download(attendanceService.queryAll(criteria), response);
//    }

//    @GetMapping
//    @Log("list api/attendance")
//    @ApiOperation("list api/attendance")
//    @PreAuthorize("@el.check('attendance:list')")
//    public ResponseEntity<Object> getAttendances(AttendanceQueryCriteria criteria, Pageable pageable){
//        return new ResponseEntity<>(attendanceService.queryAll(criteria,pageable),HttpStatus.OK);
//    }


    /**
     * 查询多天打卡记录
     * @param fromDate tpDate
     * @return /
     */
    @GetMapping("/scale")
    @Log("list api/attendance/scale")
    @ApiOperation("list api/attendance/scale")
    @PreAuthorize("@el.check('attendance:scale')")
    public ResponseEntity<Object> getAttendances(Date fromDate,Date toDate){
        List<Date> scale=new ArrayList<>();
        scale.add(fromDate);
        scale.add(toDate);
        return new ResponseEntity<>(attendanceService.queryAttendances(scale),HttpStatus.OK);
    }

    /**
     * 查询单天打卡记录
     * @param date  要查询的日期
     * @return /
     */
    @GetMapping("findbydate")
    @Log("查询 api/attendance/findbydate")
    @ApiOperation("查询 api/attendance/findbydate")
    @PreAuthorize("@el.check('attendance:findbydate')")
    public ResponseEntity<Object> getAttendance(Date date){
        return new ResponseEntity<>(attendanceService.queryAttendance(date),HttpStatus.OK);
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


    @PostMapping("/checkin")
    @Log("api/attendance/checkin")
    @ApiOperation("api/attendance/checkkin")
    @PreAuthorize("@el.check('attendance:checkin')")
    public ResponseEntity<Object> checkin(){
        return new ResponseEntity<>(attendanceService.checkin(),HttpStatus.CREATED);
    }

    @PostMapping("/checkout")
    @Log("api/attendance/checkout")
    @ApiOperation("api/attendance/checkkout")
    @PreAuthorize("@el.check('attendance:checkout')")
    public ResponseEntity<Object> checkout() throws Exception {
        return new ResponseEntity<>(attendanceService.checkout(),HttpStatus.CREATED);
    }


}