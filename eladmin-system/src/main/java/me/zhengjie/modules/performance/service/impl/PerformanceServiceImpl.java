package me.zhengjie.modules.performance.service.impl;

import me.zhengjie.modules.performance.domain.Performance;
import me.zhengjie.modules.system.domain.Role;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.repository.UserRepository;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.modules.system.service.dto.UserDto;
import me.zhengjie.utils.*;
import me.zhengjie.modules.performance.repository.PerformanceRepository;
import me.zhengjie.modules.performance.service.PerformanceService;
import me.zhengjie.modules.performance.service.dto.PerformanceDto;
import me.zhengjie.modules.performance.service.dto.PerformanceQueryCriteria;
import me.zhengjie.modules.performance.service.mapper.PerformanceMapper;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.util.*;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

/**
* @author zzy
* @date 2020-06-09
*/
@Service
//@CacheConfig(cacheNames = "performance")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class PerformanceServiceImpl implements PerformanceService {
    @Resource
    private UserRepository userRepository;
    @Resource
    private UserService userService;
    private final PerformanceRepository performanceRepository;

    private final PerformanceMapper performanceMapper;

    public PerformanceServiceImpl(PerformanceRepository performanceRepository, PerformanceMapper performanceMapper) {
        this.performanceRepository = performanceRepository;
        this.performanceMapper = performanceMapper;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(PerformanceQueryCriteria criteria, Pageable pageable){
        Page<Performance> page = performanceRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(performanceMapper::toDto));
    }

    public Map<String,Object> queryByRole(PerformanceQueryCriteria criteria, Pageable pageable) throws Exception {
        String username= SecurityUtils.getUsername();
        User currentUser=userRepository.findByUsername(username);
        boolean   isAdmin=userService.hasRoles(currentUser,"超级管理员");
        boolean  isHR=userService.hasRoles(currentUser,"HR");
        if((!isAdmin)&&(!isHR)){
            criteria.setAssessedUserId(currentUser.getId());
        }
        Page<Performance> page = performanceRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(performanceMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<PerformanceDto> queryAll(PerformanceQueryCriteria criteria){
        return performanceMapper.toDto(performanceRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public PerformanceDto findById(Long id) {
        Performance performance = performanceRepository.findById(id).orElseGet(Performance::new);
        ValidationUtil.isNull(performance.getId(),"Performance","id",id);
        return performanceMapper.toDto(performance);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public List<PerformanceDto> create(Performance resources) throws Exception {
        List<Performance> performanceList=new ArrayList<>();
        //根据部门查找员工 默认研发部的员工
        List<User> userList=userRepository.findByDeptId(2L);
        for(User user:userList){
            Performance performance=new Performance();
            performance.setName(resources.getName());
            performance.setMonth(resources.getMonth());
            performance.setUserId(user.getId());
            performance.setUserName(user.getUsername());
            UserDto director=userService.getDirector(user.getUsername()).get(0);
            performance.setAssessedUserId(director.getId());
            performance.setAssessedUserName(director.getUsername());
            performance.setDeptId(user.getDept().getId());
            performance.setDeptName(user.getDept().getName());
            performance.setJobId(user.getJob().getId());
            performance.setJobName(user.getJob().getName());
            performanceList.add(performance);
        }
        List<Performance> result=performanceRepository.saveAll(performanceList);
        return result.stream().map(performanceMapper::toDto).collect(Collectors.toList());
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Performance resources) {
        Performance performance = performanceRepository.findById(resources.getId()).orElseGet(Performance::new);
            ValidationUtil.isNull( performance.getId(),"Performance","id",resources.getId());
        performance.copy(resources);
        performanceRepository.save(performance);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            performanceRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<PerformanceDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (PerformanceDto performance : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" month",  performance.getMonth());
            map.put(" userId",  performance.getUserId());
            map.put(" name",  performance.getName());
            map.put(" userName",  performance.getUserName());
            map.put(" deptId",  performance.getDeptId());
            map.put(" deptName",  performance.getDeptName());
            map.put(" jobId",  performance.getJobId());
            map.put(" jobName",  performance.getJobName());
            map.put(" taskScore",  performance.getTaskScore());
            map.put(" monthlyScore",  performance.getMonthlyScore());
            map.put(" finalScore",  performance.getFinalScore());
            map.put(" comment",  performance.getComment());
            map.put(" updateTime",  performance.getUpdateTime());
            map.put(" createTime",  performance.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public List<PerformanceDto> getMyPerformance(Pageable pageable) {
        PerformanceQueryCriteria criteria=new PerformanceQueryCriteria();
        String userName=SecurityUtils.getUsername();
        User user=userRepository.findByUsername(userName);
        criteria.setUserId(user.getId());
        Page<Performance> page = performanceRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return page.map(performanceMapper::toDto).getContent();
    }
}
