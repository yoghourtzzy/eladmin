package me.zhengjie.modules.attendance.service.impl;

import me.zhengjie.modules.attendance.domain.Attendance;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.repository.UserRepository;
import me.zhengjie.utils.*;
import me.zhengjie.modules.attendance.repository.AttendanceRepository;
import me.zhengjie.modules.attendance.service.AttendanceService;
import me.zhengjie.modules.attendance.service.dto.AttendanceDto;
import me.zhengjie.modules.attendance.service.dto.AttendanceQueryCriteria;
import me.zhengjie.modules.attendance.service.mapper.AttendanceMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @author zzy
* @date 2020-04-26
*/
@Service
//@CacheConfig(cacheNames = "attendance")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    private final AttendanceMapper attendanceMapper;
    @Resource
    private UserRepository userRepository;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository, AttendanceMapper attendanceMapper) {
        this.attendanceRepository = attendanceRepository;
        this.attendanceMapper = attendanceMapper;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(AttendanceQueryCriteria criteria, Pageable pageable){
        Page<Attendance> page = attendanceRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(attendanceMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<AttendanceDto> queryAll(AttendanceQueryCriteria criteria){
        return attendanceMapper.toDto(attendanceRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public AttendanceDto findById(Long id) {
        Attendance attendance = attendanceRepository.findById(id).orElseGet(Attendance::new);
        ValidationUtil.isNull(attendance.getId(),"Attendance","id",id);
        return attendanceMapper.toDto(attendance);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public AttendanceDto create(Attendance resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setId(snowflake.nextId()); 
        return attendanceMapper.toDto(attendanceRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Attendance resources) {
        Attendance attendance = attendanceRepository.findById(resources.getId()).orElseGet(Attendance::new);
        ValidationUtil.isNull( attendance.getId(),"Attendance","id",resources.getId());
        attendance.copy(resources);
        attendanceRepository.save(attendance);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            attendanceRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<AttendanceDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (AttendanceDto attendance : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" userId",  attendance.getUserId());
            map.put("所记录的日期", attendance.getRecDate());
            map.put(" state",  attendance.getState());
            map.put(" startTime",  attendance.getStartTime());
            map.put(" finishTime",  attendance.getFinishTime());
            map.put(" updateTime",  attendance.getUpdateTime());
            map.put(" createTime",  attendance.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public AttendanceDto checkin() {
        Attendance attendance;

        //已经有当天记录的情况
        if((attendance=attendanceRepository.findByRecDate(new Date(System.currentTimeMillis())))!=null){
            if(attendance.getState()!=0){
                //请假或者出差 覆盖记录 重复打卡,返回上次打卡记录
                attendance.setState(0);
                attendance.setStartTime(new Timestamp(System.currentTimeMillis()));
                attendanceRepository.save(attendance);
            }
            return attendanceMapper.toDto(attendance);
        }

        //没有当天记录的情况
        String username= SecurityUtils.getUsername();
        User user=userRepository.findByUsername(username);
        attendance=new Attendance();
        attendance.setState(0);
        attendance.setStartTime(new Timestamp(System.currentTimeMillis()));
        attendance.setUserId(user.getId());
        return  attendanceMapper.toDto(attendanceRepository.save(attendance));
    }
}