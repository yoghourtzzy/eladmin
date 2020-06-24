package me.zhengjie.modules.recruitment.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.recruitment.domain.RecruitmentJob;
import me.zhengjie.modules.recruitment.service.RecruitmentJobService;
import me.zhengjie.modules.recruitment.service.dto.RecruitmentJobQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author zzy
* @date 2020-05-20
*/
@Api(tags = "api/recuitment/job管理")
@RestController
@RequestMapping("/api/recruitment/job")
public class RecruitmentJobController {

    private final RecruitmentJobService recruitmentJobService;

    public RecruitmentJobController(RecruitmentJobService recruitmentJobService) {
        this.recruitmentJobService = recruitmentJobService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
//    @PreAuthorize("@el.check('recruitmentJob:list')")
    public void download(HttpServletResponse response,      RecruitmentJobQueryCriteria criteria) throws IOException {
        recruitmentJobService.download(recruitmentJobService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/recuitment/job")
    @ApiOperation("查询api/recuitment/job")
    public ResponseEntity<Object> getRecruitmentJobs(RecruitmentJobQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(recruitmentJobService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增api/recuitment/job")
    @ApiOperation("新增api/recuitment/job")
//    @PreAuthorize("@el.check('recruitmentJob:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody RecruitmentJob resources){
        return new ResponseEntity<>(recruitmentJobService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/recuitment/job")
    @ApiOperation("修改api/recuitment/job")
//    @PreAuthorize("@el.check('recruitmentJob:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody RecruitmentJob resources){
        recruitmentJobService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除api/recuitment/job")
    @ApiOperation("删除api/recuitment/job")
//    @PreAuthorize("@el.check('recruitmentJob:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        recruitmentJobService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
