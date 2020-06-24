package me.zhengjie.modules.recruitment.service;

import me.zhengjie.modules.recruitment.domain.RecruitmentJob;
import me.zhengjie.modules.recruitment.service.dto.RecruitmentJobDto;
import me.zhengjie.modules.recruitment.service.dto.RecruitmentJobQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author zzy
* @date 2020-05-20
*/
public interface RecruitmentJobService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(RecruitmentJobQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<RecruitmentJobDto>
    */
    List<RecruitmentJobDto> queryAll(RecruitmentJobQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return RecruitmentJobDto
     */
    RecruitmentJobDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return RecruitmentJobDto
    */
    RecruitmentJobDto create(RecruitmentJob resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(RecruitmentJob resources);

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
    void download(List<RecruitmentJobDto> all, HttpServletResponse response) throws IOException;
}