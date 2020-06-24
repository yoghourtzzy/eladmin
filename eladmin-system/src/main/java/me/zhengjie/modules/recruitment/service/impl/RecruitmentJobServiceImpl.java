package me.zhengjie.modules.recruitment.service.impl;

import me.zhengjie.modules.recruitment.domain.RecruitmentJob;
import me.zhengjie.modules.recruitment.service.RecruitmentPeopleService;
import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.domain.Job;
import me.zhengjie.modules.system.service.DeptService;
import me.zhengjie.modules.system.service.JobService;
import me.zhengjie.modules.system.service.dto.DeptDto;
import me.zhengjie.modules.system.service.dto.JobDto;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.modules.recruitment.repository.RecruitmentJobRepository;
import me.zhengjie.modules.recruitment.service.RecruitmentJobService;
import me.zhengjie.modules.recruitment.service.dto.RecruitmentJobDto;
import me.zhengjie.modules.recruitment.service.dto.RecruitmentJobQueryCriteria;
import me.zhengjie.modules.recruitment.service.mapper.RecruitmentJobMapper;
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
* @date 2020-05-20
*/
@Service
//@CacheConfig(cacheNames = "recruitmentJob")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class RecruitmentJobServiceImpl implements RecruitmentJobService {

    private final RecruitmentJobRepository recruitmentJobRepository;

    private final RecruitmentJobMapper recruitmentJobMapper;
    @Resource
    private JobService jobService;
    @Resource
    private DeptService deptService;
    @Resource
    private RecruitmentPeopleService recruitmentPeopleService;
    public RecruitmentJobServiceImpl(RecruitmentJobRepository recruitmentJobRepository, RecruitmentJobMapper recruitmentJobMapper) {
        this.recruitmentJobRepository = recruitmentJobRepository;
        this.recruitmentJobMapper = recruitmentJobMapper;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(RecruitmentJobQueryCriteria criteria, Pageable pageable){
        Page<RecruitmentJob> page = recruitmentJobRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        Page<RecruitmentJobDto> result=page.map(recruitmentJobMapper::toDto);
        for(RecruitmentJobDto dto:result.getContent()){
            dto.setAcceptResume(recruitmentPeopleService.getAcceptResumeNum(dto.getId()));
            dto.setAcceptOffer(recruitmentPeopleService.getAcceptOfferNum(dto.getId()));
        }
        return PageUtil.toPage(result);
    }

    @Override
    //@Cacheable
    public List<RecruitmentJobDto> queryAll(RecruitmentJobQueryCriteria criteria){
        return recruitmentJobMapper.toDto(recruitmentJobRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public RecruitmentJobDto findById(Long id) {
        RecruitmentJob recruitmentJob = recruitmentJobRepository.findById(id).orElseGet(RecruitmentJob::new);
        ValidationUtil.isNull(recruitmentJob.getId(),"RecruitmentJob","id",id);
        return recruitmentJobMapper.toDto(recruitmentJob);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public RecruitmentJobDto create(RecruitmentJob resources) {
        resources.setState(0);
        DeptDto dept=deptService.findById(resources.getDeptId());
        resources.setDeptName(dept.getName());
        JobDto job=jobService.findById(resources.getJobId());
        resources.setJobName(job.getName());
        return recruitmentJobMapper.toDto(recruitmentJobRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(RecruitmentJob resources) {
        RecruitmentJob recruitmentJob = recruitmentJobRepository.findById(resources.getId()).orElseGet(RecruitmentJob::new);
        ValidationUtil.isNull( recruitmentJob.getId(),"RecruitmentJob","id",resources.getId());
        recruitmentJob.copy(resources);
        recruitmentJobRepository.save(recruitmentJob);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            recruitmentJobRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<RecruitmentJobDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (RecruitmentJobDto recruitmentJob : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("关联岗位", recruitmentJob.getJobName());
            map.put(" name",  recruitmentJob.getName());
            map.put(" jobId",  recruitmentJob.getJobId());
            map.put("0 全职 1劳务派遣 2实习", recruitmentJob.getJobType());
            map.put(" deptName",  recruitmentJob.getDeptName());
            map.put(" deptId",  recruitmentJob.getDeptId());
            map.put("0已完成 1未完成", recruitmentJob.getState());
            map.put(" needNum",  recruitmentJob.getNeedNum());
            map.put(" createTime",  recruitmentJob.getCreateTime());
            map.put(" updateTime",  recruitmentJob.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}
