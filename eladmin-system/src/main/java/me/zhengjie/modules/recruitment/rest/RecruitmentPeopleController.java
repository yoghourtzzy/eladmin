package me.zhengjie.modules.recruitment.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.recruitment.domain.RecruitmentPeople;
import me.zhengjie.modules.recruitment.service.RecruitmentPeopleService;
import me.zhengjie.modules.recruitment.service.dto.RecruitmentPeopleQueryCriteria;
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
* @date 2020-06-08
*/
@Api(tags = "api/recruitment/people管理")
@RestController
@RequestMapping("/api/recruitment/people")
public class RecruitmentPeopleController {

    private final RecruitmentPeopleService recruitmentPeopleService;

    public RecruitmentPeopleController(RecruitmentPeopleService recruitmentPeopleService) {
        this.recruitmentPeopleService = recruitmentPeopleService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
   // //@PreAuthorize("@el.check('recruitmentPeople:list')")
    public void download(HttpServletResponse response, RecruitmentPeopleQueryCriteria criteria) throws IOException {
        recruitmentPeopleService.download(recruitmentPeopleService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/recuitment/people")
    @ApiOperation("查询api/recuitment/people")
   // //@PreAuthorize("@el.check('recruitmentPeople:list')")
    public ResponseEntity<Object> getRecruitmentPeoples(RecruitmentPeopleQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(recruitmentPeopleService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增api/recuitment/people")
    @ApiOperation("新增api/recuitment/people")
    //@PreAuthorize("@el.check('recruitmentPeople:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody RecruitmentPeople resources){
        return new ResponseEntity<>(recruitmentPeopleService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/recuitment/people")
    @ApiOperation("修改api/recuitment/people")
    //@PreAuthorize("@el.check('recruitmentPeople:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody RecruitmentPeople resources){
        recruitmentPeopleService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除api/recuitment/people")
    @ApiOperation("删除api/recuitment/people")
    //@PreAuthorize("@el.check('recruitmentPeople:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        recruitmentPeopleService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
