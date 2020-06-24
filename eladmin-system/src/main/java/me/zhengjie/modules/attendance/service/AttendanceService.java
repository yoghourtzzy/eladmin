package me.zhengjie.modules.attendance.service;

import me.zhengjie.modules.attendance.domain.Attendance;
import me.zhengjie.modules.attendance.service.dto.AttendanceDto;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author zzy
* @date 2020-04-26
*/
public interface AttendanceService {

//    /**
//    * 查询数据分页
//    * @param criteria 条件
//    * @param pageable 分页参数
//    * @return Map<String,Object>
//    */
//    Map<String,Object> queryAll(AttendanceQueryCriteria criteria, Pageable pageable);
//
//    /**
//    * 查询所有数据不分页
//    * @param criteria 条件参数
//    * @return List<AttendanceDto>
//    */
//    List<AttendanceDto> queryAll(AttendanceQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return AttendanceDto
     */
    AttendanceDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return AttendanceDto
    */
    AttendanceDto create(Attendance resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(Attendance resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<AttendanceDto> all, HttpServletResponse response) throws IOException;

    /**
     * 签到功能
     * @return /
     */
    AttendanceDto checkin();

    /**
     * 签退功能
     * @return /
     */
    AttendanceDto checkout() throws Exception;

    /**
     * 查询当前用户某个日期范围内的考勤记录
     * @param scale 日期范围
     * @return list
     */
    List<AttendanceDto> queryAttendances(List<Date> scale);

    /**
     * 查询当前用户某天的考勤记录
     * @param date 要查询的日期
     * @return AttendanceDto
     */
    AttendanceDto  queryAttendance(Date date);

}