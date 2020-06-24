package me.zhengjie.modules.performance.rest;

import me.zhengjie.aop.log.Log;
import me.zhengjie.modules.performance.domain.Performance;
import me.zhengjie.modules.performance.service.PerformanceService;
import me.zhengjie.modules.performance.service.dto.PerformanceQueryCriteria;
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
* @date 2020-06-09
*/
@Api(tags = "api/performance管理")
@RestController
@RequestMapping("/api/performance")
public class PerformanceController {

    private final PerformanceService performanceService;

    public PerformanceController(PerformanceService performanceService) {
        this.performanceService = performanceService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
     //@PreAuthorize("@el.check('performance:list')")
    public void download(HttpServletResponse response, PerformanceQueryCriteria criteria) throws IOException {
        performanceService.download(performanceService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/performance")
    @ApiOperation("查询api/performance")
     //@PreAuthorize("@el.check('performance:list')")
    public ResponseEntity<Object> getPerformances(PerformanceQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(performanceService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @GetMapping("/byrole")
    @Log("查询api/performance/byrole")
    @ApiOperation("查询api/performance/byrole")
    //@PreAuthorize("@el.check('performance:list')")
    public ResponseEntity<Object> getPerformanceByrole(PerformanceQueryCriteria criteria, Pageable pageable) throws Exception {
        return new ResponseEntity<>(performanceService.queryByRole(criteria,pageable),HttpStatus.OK);
    }

    @GetMapping("/myperformance")
    @Log("查询api/performance/myperformance")
    @ApiOperation("查询api/performance/myperformance")
    //@PreAuthorize("@el.check('performance:list')")
    public ResponseEntity<Object> getMyPerformance(Pageable pageable) throws Exception {
        return new ResponseEntity<>(performanceService.getMyPerformance(pageable),HttpStatus.OK);
    }


    @PostMapping
    @Log("新增api/performance")
    @ApiOperation("新增api/performance")
     //@PreAuthorize("@el.check('performance:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Performance resources) throws Exception {
        return new ResponseEntity<>(performanceService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/performance")
    @ApiOperation("修改api/performance")
     //@PreAuthorize("@el.check('performance:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Performance resources){
        performanceService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除api/performance")
    @ApiOperation("删除api/performance")
     //@PreAuthorize("@el.check('performance:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        performanceService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
