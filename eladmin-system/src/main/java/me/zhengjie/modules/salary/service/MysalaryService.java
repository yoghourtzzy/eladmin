package me.zhengjie.modules.salary.service;

import me.zhengjie.modules.salary.domain.Mysalary;
import me.zhengjie.modules.salary.service.dto.MysalaryDto;
import me.zhengjie.modules.salary.service.dto.MysalaryQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author zzy
* @date 2020-06-17
*/
public interface MysalaryService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(MysalaryQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<MysalaryDto>
    */
    List<MysalaryDto> queryAll(MysalaryQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return MysalaryDto
     */
    MysalaryDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return MysalaryDto
    */
    MysalaryDto create(Mysalary resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(Mysalary resources);

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
    void download(List<MysalaryDto> all, HttpServletResponse response) throws IOException;
}