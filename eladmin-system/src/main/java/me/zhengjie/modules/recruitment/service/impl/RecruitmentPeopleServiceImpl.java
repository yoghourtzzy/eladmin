package me.zhengjie.modules.recruitment.service.impl;

import me.zhengjie.modules.recruitment.domain.RecruitmentJob;
import me.zhengjie.modules.recruitment.domain.RecruitmentPeople;
import me.zhengjie.modules.recruitment.repository.RecruitmentJobRepository;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.modules.recruitment.repository.RecruitmentPeopleRepository;
import me.zhengjie.modules.recruitment.service.RecruitmentPeopleService;
import me.zhengjie.modules.recruitment.service.dto.RecruitmentPeopleDto;
import me.zhengjie.modules.recruitment.service.dto.RecruitmentPeopleQueryCriteria;
import me.zhengjie.modules.recruitment.service.mapper.RecruitmentPeopleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @author zzy
* @date 2020-06-08
*/
@Service
//@CacheConfig(cacheNames = "recruitmentPeople")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RecruitmentPeopleServiceImpl implements RecruitmentPeopleService {

    private final RecruitmentPeopleRepository recruitmentPeopleRepository;

    private final RecruitmentPeopleMapper recruitmentPeopleMapper;
    @Resource
    private RecruitmentJobRepository recruitmentJobRepository;

    public RecruitmentPeopleServiceImpl(RecruitmentPeopleRepository recruitmentPeopleRepository, RecruitmentPeopleMapper recruitmentPeopleMapper) {
        this.recruitmentPeopleRepository = recruitmentPeopleRepository;
        this.recruitmentPeopleMapper = recruitmentPeopleMapper;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(RecruitmentPeopleQueryCriteria criteria, Pageable pageable){
        Page<RecruitmentPeople> page = recruitmentPeopleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(recruitmentPeopleMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<RecruitmentPeopleDto> queryAll(RecruitmentPeopleQueryCriteria criteria){
        return recruitmentPeopleMapper.toDto(recruitmentPeopleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public RecruitmentPeopleDto findById(Long id) {
        RecruitmentPeople recruitmentPeople = recruitmentPeopleRepository.findById(id).orElseGet(RecruitmentPeople::new);
        ValidationUtil.isNull(recruitmentPeople.getId(),"RecruitmentPeople","id",id);
        return recruitmentPeopleMapper.toDto(recruitmentPeople);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public RecruitmentPeopleDto create(RecruitmentPeople resources) {
        RecruitmentJob recruitmentJob = recruitmentJobRepository.findById(resources.getRecruitmentJobId()).get();
        resources.setRecruitmentJobName(recruitmentJob.getName());
        resources.setProcess(0);
        resources.setResumeState(0);
        resources.setOfferState(0);
        resources.setInterviewState(0);
        return recruitmentPeopleMapper.toDto(recruitmentPeopleRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(RecruitmentPeople resources) {
        RecruitmentPeople recruitmentPeople = recruitmentPeopleRepository.findById(resources.getId()).orElseGet(RecruitmentPeople::new);
        ValidationUtil.isNull( recruitmentPeople.getId(),"RecruitmentPeople","id",resources.getId());
        recruitmentPeople.copy(resources);
        recruitmentPeopleRepository.save(recruitmentPeople);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            recruitmentPeopleRepository.deleteById(id);
        }
    }


    @Override
    public void download(List<RecruitmentPeopleDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (RecruitmentPeopleDto recruitmentPeople : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" name",  recruitmentPeople.getName());
            map.put(" age",  recruitmentPeople.getAge());
            map.put(" sex",  recruitmentPeople.getSex());
            map.put(" email",  recruitmentPeople.getEmail());
            map.put(" phone",  recruitmentPeople.getPhone());
            map.put("毕业学校", recruitmentPeople.getSchool());
            map.put(" recruitmentJobId",  recruitmentPeople.getRecruitmentJobId());
            map.put(" resumeFileId",  recruitmentPeople.getResumeFileId());
            map.put(" process",  recruitmentPeople.getProcess());
            map.put("简历状态/0带筛选/1淘汰/2通过", recruitmentPeople.getResumeState());
            map.put("面试状态/0未进面/1 1面/2 2面/3 3面 /4 淘汰", recruitmentPeople.getInterviewState());
            map.put("0未发送  1未回复  1同意 2不同意", recruitmentPeople.getOfferState());
            map.put(" createTime",  recruitmentPeople.getCreateTime());
            map.put(" updateTime",  recruitmentPeople.getUpdateTime());
            list.add(map);

        }
        FileUtil.downloadExcel(list, response);

    }

    @Override
    public Integer getAcceptResumeNum(Long rJobId) {
        RecruitmentPeopleQueryCriteria criteria=new RecruitmentPeopleQueryCriteria();
        criteria.setRecruitmentJobId(rJobId);
        List<RecruitmentPeopleDto> list=recruitmentPeopleMapper.toDto(recruitmentPeopleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
        return list.size();
    }

    @Override
    public Integer getAcceptOfferNum(Long rJobId) {
        RecruitmentPeopleQueryCriteria criteria=new RecruitmentPeopleQueryCriteria();
        criteria.setRecruitmentJobId(rJobId);
        criteria.setProcess(2);
        criteria.setOfferState(1);
        List<RecruitmentPeopleDto> list=recruitmentPeopleMapper.toDto(recruitmentPeopleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
        return list.size();
    }
}
