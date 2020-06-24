package me.zhengjie.modules.attendance.service;

import me.zhengjie.modules.attendance.domain.AttendanceApply;
import me.zhengjie.modules.attendance.service.dto.AttendanceApplyDto;
import me.zhengjie.modules.attendance.service.dto.AttendanceApplyQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author zzy
* @date 2020-05-05
*/
public interface AttendanceApplyService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(AttendanceApplyQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<AttendanceApplyDto>
    */
    List<AttendanceApplyDto> queryAll(AttendanceApplyQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return AttendanceApplyDto
     */
    AttendanceApplyDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return AttendanceApplyDto
    */
    AttendanceApplyDto create(AttendanceApply resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(AttendanceApply resources);

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
    void download(List<AttendanceApplyDto> all, HttpServletResponse response) throws IOException;

    /**
     * 查询我的申请
     * @param criteria
     * @param pageable
     * @return
     */
    Map<String,Object>  queryMyApplys(AttendanceApplyQueryCriteria criteria, Pageable pageable);

    /**
     * 查询需要我审批的申请
     * @param criteria
     * @param pageable
     * @return
     */
    Map<String,Object>   queryMyApprovals(AttendanceApplyQueryCriteria criteria, Pageable pageable);

    AttendanceApplyDto  approve(AttendanceApply resources) throws Exception;
}