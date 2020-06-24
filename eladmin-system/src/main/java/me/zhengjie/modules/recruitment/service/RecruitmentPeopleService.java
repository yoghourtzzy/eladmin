package me.zhengjie.modules.recruitment.service;

import me.zhengjie.modules.recruitment.domain.RecruitmentPeople;
import me.zhengjie.modules.recruitment.service.dto.RecruitmentPeopleDto;
import me.zhengjie.modules.recruitment.service.dto.RecruitmentPeopleQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author zzy
* @date 2020-06-08
*/
public interface RecruitmentPeopleService {
    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(RecruitmentPeopleQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<RecruitmentPeopleDto>
    */
    List<RecruitmentPeopleDto> queryAll(RecruitmentPeopleQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return RecruitmentPeopleDto
     */
    RecruitmentPeopleDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return RecruitmentPeopleDto
    */
    RecruitmentPeopleDto create(RecruitmentPeople resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(RecruitmentPeople resources);

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
    void download(List<RecruitmentPeopleDto> all, HttpServletResponse response) throws IOException;

    /**
     * @return 获取接收简历的份数
     */
    public  Integer getAcceptResumeNum(Long rJobId);

    /**
     * @return 获取入职的人数
     */
    public  Integer getAcceptOfferNum(Long rJobId);


}
