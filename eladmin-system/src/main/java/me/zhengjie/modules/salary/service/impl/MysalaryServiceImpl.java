package me.zhengjie.modules.salary.service.impl;

import me.zhengjie.modules.salary.domain.Mysalary;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.modules.salary.repository.MysalaryRepository;
import me.zhengjie.modules.salary.service.MysalaryService;
import me.zhengjie.modules.salary.service.dto.MysalaryDto;
import me.zhengjie.modules.salary.service.dto.MysalaryQueryCriteria;
import me.zhengjie.modules.salary.service.mapper.MysalaryMapper;
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
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @author zzy
* @date 2020-06-17
*/
@Service
//@CacheConfig(cacheNames = "mysalary")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MysalaryServiceImpl implements MysalaryService {

    private final MysalaryRepository mysalaryRepository;

    private final MysalaryMapper mysalaryMapper;

    public MysalaryServiceImpl(MysalaryRepository mysalaryRepository, MysalaryMapper mysalaryMapper) {
        this.mysalaryRepository = mysalaryRepository;
        this.mysalaryMapper = mysalaryMapper;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(MysalaryQueryCriteria criteria, Pageable pageable){
        Page<Mysalary> page = mysalaryRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(mysalaryMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<MysalaryDto> queryAll(MysalaryQueryCriteria criteria){
        return mysalaryMapper.toDto(mysalaryRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public MysalaryDto findById(Long id) {
        Mysalary mysalary = mysalaryRepository.findById(id).orElseGet(Mysalary::new);
        ValidationUtil.isNull(mysalary.getId(),"Mysalary","id",id);
        return mysalaryMapper.toDto(mysalary);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public MysalaryDto create(Mysalary resources) {
        return mysalaryMapper.toDto(mysalaryRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Mysalary resources) {
        Mysalary mysalary = mysalaryRepository.findById(resources.getId()).orElseGet(Mysalary::new);
        ValidationUtil.isNull( mysalary.getId(),"Mysalary","id",resources.getId());
        mysalary.copy(resources);
        mysalaryRepository.save(mysalary);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            mysalaryRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<MysalaryDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MysalaryDto mysalary : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" userId",  mysalary.getUserId());
            map.put(" userName",  mysalary.getUserName());
            map.put(" trafficSalary",  mysalary.getTrafficSalary());
            map.put(" lunchSalary",  mysalary.getLunchSalary());
            map.put(" finalBonus",  mysalary.getFinalBonus());
            map.put(" insurance",  mysalary.getInsurance());
            map.put(" tax",  mysalary.getTax());
            map.put(" finalSalary",  mysalary.getFinalSalary());
            map.put(" updateTime",  mysalary.getUpdateTime());
            map.put(" createTime",  mysalary.getCreateTime());
            map.put(" month",  mysalary.getMonth());
            map.put(" attendanceDeduction",  mysalary.getAttendanceDeduction());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}