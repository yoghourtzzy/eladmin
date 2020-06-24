package me.zhengjie.modules.attendance.service.impl;

import me.zhengjie.modules.attendance.domain.Attendance;
import me.zhengjie.modules.attendance.domain.AttendanceApply;
import me.zhengjie.modules.attendance.repository.AttendanceRepository;
import me.zhengjie.modules.attendance.service.AttendanceService;
import me.zhengjie.modules.attendance.service.dto.AttendanceDateScaleQueryCriteria;
import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.repository.UserRepository;
import me.zhengjie.utils.*;
import me.zhengjie.modules.attendance.repository.AttendanceApplyRepository;
import me.zhengjie.modules.attendance.service.AttendanceApplyService;
import me.zhengjie.modules.attendance.service.dto.AttendanceApplyDto;
import me.zhengjie.modules.attendance.service.dto.AttendanceApplyQueryCriteria;
import me.zhengjie.modules.attendance.service.mapper.AttendanceApplyMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
* @author zzy
* @date 2020-05-05
*/
@Service
//@CacheConfig(cacheNames = "attendanceApply")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AttendanceApplyServiceImpl implements AttendanceApplyService {

    private final AttendanceApplyRepository attendanceApplyRepository;

    private final AttendanceApplyMapper attendanceApplyMapper;
    @Resource
    private UserRepository userRepository;
    @Resource
    private AttendanceRepository attendanceRepository;
    @Resource
    private AttendanceService attendanceService;

    public AttendanceApplyServiceImpl(AttendanceApplyRepository attendanceApplyRepository, AttendanceApplyMapper attendanceApplyMapper) {
        this.attendanceApplyRepository = attendanceApplyRepository;
        this.attendanceApplyMapper = attendanceApplyMapper;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(AttendanceApplyQueryCriteria criteria, Pageable pageable){
        Page<AttendanceApply> page = attendanceApplyRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(attendanceApplyMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<AttendanceApplyDto> queryAll(AttendanceApplyQueryCriteria criteria){
        return attendanceApplyMapper.toDto(attendanceApplyRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public AttendanceApplyDto findById(Long id) {
        AttendanceApply attendanceApply = attendanceApplyRepository.findById(id).orElseGet(AttendanceApply::new);
        ValidationUtil.isNull(attendanceApply.getId(),"AttendanceApply","id",id);
        return attendanceApplyMapper.toDto(attendanceApply);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public AttendanceApplyDto create(AttendanceApply resources) {
        String username= SecurityUtils.getUsername();
        User currentUser=userRepository.findByUsername(username);
        resources.setFromUserId(currentUser.getId());
        resources.setFromUserName(currentUser.getUsername());

        User toUser=userRepository.findById(resources.getToUserId()).get();
        resources.setToUserName(toUser.getUsername());
        resources.setState(0);
        return attendanceApplyMapper.toDto(attendanceApplyRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(AttendanceApply resources) {
        AttendanceApply attendanceApply = attendanceApplyRepository.findById(resources.getId()).orElseGet(AttendanceApply::new);
        ValidationUtil.isNull( attendanceApply.getId(),"AttendanceApply","id",resources.getId());
        attendanceApply.copy(resources);
        attendanceApplyRepository.save(attendanceApply);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            attendanceApplyRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<AttendanceApplyDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (AttendanceApplyDto attendanceApply : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("申请人姓名", attendanceApply.getFromUserName());
            map.put(" fromUserId",  attendanceApply.getFromUserId());
            map.put(" toUserName",  attendanceApply.getToUserName());
            map.put(" toUserId",  attendanceApply.getToUserId());
            map.put("开始时间", attendanceApply.getStartDate());
            map.put("结束时间", attendanceApply.getFinishDate());
            map.put("申请原因", attendanceApply.getReason());
            map.put("0请假  1出差", attendanceApply.getType());
            map.put("0未审批 1审批同意 2审批不同意", attendanceApply.getState());
            map.put("审批意见/备注", attendanceApply.getComment());
            map.put(" createTime",  attendanceApply.getCreateTime());
            map.put(" updateTime",  attendanceApply.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public Map<String, Object> queryMyApplys(AttendanceApplyQueryCriteria criteria, Pageable pageable) {
        String username= SecurityUtils.getUsername();
        User currentUser=userRepository.findByUsername(username);
        criteria.setFromUserId(currentUser.getId());
        Page<AttendanceApply> page = attendanceApplyRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(attendanceApplyMapper::toDto));
    }

    @Override
    public Map<String, Object> queryMyApprovals(AttendanceApplyQueryCriteria criteria, Pageable pageable) {
        String username= SecurityUtils.getUsername();
        User currentUser=userRepository.findByUsername(username);
        criteria.setToUserId(currentUser.getId());
        Page<AttendanceApply> page = attendanceApplyRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(attendanceApplyMapper::toDto));
    }

    @Override
    public AttendanceApplyDto approve(AttendanceApply resources) throws Exception {
       //resources中只有id和state两条信息  state表示通过和未通过

        //更新申请信息
        AttendanceApply attendanceApply=attendanceApplyRepository.findById(resources.getId()).get();
        if(attendanceApply==null){
            throw new Exception("申请已删除");
        }
        attendanceApply.setState(resources.getState());
        attendanceApplyRepository.save(attendanceApply);



        //如果审批通过 更新签到信息
        if(resources.getState()==1){
//            AttendanceDateScaleQueryCriteria criteria=new AttendanceDateScaleQueryCriteria();
//            List<Date> dateScale=new ArrayList<>();
//            dateScale.add(attendanceApply.getStartDate());
//            dateScale.add(attendanceApply.getFinishDate());
//            criteria.setRecDate(dateScale);
//            List<Attendance> tempList=attendanceRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder));
//            Map<Date,Attendance> recAttendanceMap=tempList.stream().collect(Collectors.toMap(Attendance::getRecDate, attendance -> attendance));

            LocalDate startDate=attendanceApply.getStartDate().toLocalDate();
            LocalDate finishDate=attendanceApply.getFinishDate().toLocalDate();
            Period period=Period.between(startDate,finishDate);

            //recDate从satrtDate到finishDate 如果recDate已有记录 更新记录
            LocalDate recDate=attendanceApply.getStartDate().toLocalDate();
            int days=period.getDays();
            for(int i=0;i<=days;++i){
                recDate=recDate.plusDays(i);
                Attendance attendance=attendanceRepository.findByRecDate(Date.valueOf(recDate));
                if(attendance==null){
                    attendance=new Attendance();
                    attendance.setRecDate(Date.valueOf(recDate));
                }
                attendance.setUserId(attendanceApply.getFromUserId());
                attendance.setState(attendanceApply.getType()+1);
                attendanceRepository.save(attendance);
            }
        }
        return attendanceApplyMapper.toDto(attendanceApply);
    }

}