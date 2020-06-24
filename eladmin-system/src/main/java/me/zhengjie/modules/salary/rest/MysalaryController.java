package me.zhengjie.modules.salary.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.salary.domain.Mysalary;
import me.zhengjie.modules.salary.service.MysalaryService;
import me.zhengjie.modules.salary.service.dto.MysalaryQueryCriteria;
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
* @date 2020-06-17
*/
@Api(tags = "api/salary/mysalary管理")
@RestController
@RequestMapping("/api/mysalary")
public class MysalaryController {

    private final MysalaryService mysalaryService;

    public MysalaryController(MysalaryService mysalaryService) {
        this.mysalaryService = mysalaryService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('mysalary:list')")
    public void download(HttpServletResponse response, MysalaryQueryCriteria criteria) throws IOException {
        mysalaryService.download(mysalaryService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/salary/mysalary")
    @ApiOperation("查询api/salary/mysalary")
    @PreAuthorize("@el.check('mysalary:list')")
    public ResponseEntity<Object> getMysalarys(MysalaryQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(mysalaryService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增api/salary/mysalary")
    @ApiOperation("新增api/salary/mysalary")
    @PreAuthorize("@el.check('mysalary:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Mysalary resources){
        return new ResponseEntity<>(mysalaryService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/salary/mysalary")
    @ApiOperation("修改api/salary/mysalary")
    @PreAuthorize("@el.check('mysalary:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Mysalary resources){
        mysalaryService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除api/salary/mysalary")
    @ApiOperation("删除api/salary/mysalary")
    @PreAuthorize("@el.check('mysalary:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        mysalaryService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}