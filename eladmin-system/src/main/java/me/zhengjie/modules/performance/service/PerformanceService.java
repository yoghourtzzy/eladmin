package me.zhengjie.modules.performance.service;

import me.zhengjie.modules.performance.domain.Performance;
import me.zhengjie.modules.performance.service.dto.PerformanceDto;
import me.zhengjie.modules.performance.service.dto.PerformanceQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author zzy
* @date 2020-06-09
*/
public interface PerformanceService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(PerformanceQueryCriteria criteria, Pageable pageable);

    /**
     * 根据角色查询
     * @param criteria
     * @param pageable
     * @return
     */
    Map<String,Object> queryByRole(PerformanceQueryCriteria criteria, Pageable pageable) throws Exception;

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<PerformanceDto>
    */
    List<PerformanceDto> queryAll(PerformanceQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return PerformanceDto
     */
    PerformanceDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return PerformanceDto
    */
    List<PerformanceDto> create(Performance resources) throws Exception;

    /**
    * 编辑
    * @param resources /
    */
    void update(Performance resources);

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
    void download(List<PerformanceDto> all, HttpServletResponse response) throws IOException;

    List<PerformanceDto>  getMyPerformance(Pageable pageable);
}
