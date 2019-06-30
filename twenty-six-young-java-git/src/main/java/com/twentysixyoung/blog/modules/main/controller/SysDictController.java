package com.twentysixyoung.blog.modules.main.controller;

import java.util.Arrays;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import com.twentysixyoung.blog.common.utils.QueryGenerator;
import com.twentysixyoung.blog.common.vo.Result;
import com.twentysixyoung.blog.modules.main.entity.SysDict;
import com.twentysixyoung.blog.modules.main.service.ISysDictService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @Description: 字典表
 * @Author: jeecg-boot
 * @Date: 2019-06-24
 * @Version: V1.0
 */
@Slf4j
@Api(tags = "字典表")
@RestController
@RequestMapping("/main/sysDict")
public class SysDictController {
    @Autowired
    private ISysDictService sysDictService;

    /**
     * 分页列表查询
     *
     * @param sysDict
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "字典表-分页列表查询", notes = "字典表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<SysDict>> queryPageList(SysDict sysDict,
                                                @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                HttpServletRequest req) {
        Result<IPage<SysDict>> result = new Result<IPage<SysDict>>();
        QueryWrapper<SysDict> queryWrapper = QueryGenerator.initQueryWrapper(sysDict, req.getParameterMap());
        Page<SysDict> page = new Page<SysDict>(pageNo, pageSize);
        IPage<SysDict> pageList = sysDictService.page(page, queryWrapper);
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }

    /**
     * 添加
     *
     * @param sysDict
     * @return
     */
    @ApiOperation(value = "字典表-添加", notes = "字典表-添加")
    @PostMapping(value = "/add")
    public Result<SysDict> add(@RequestBody SysDict sysDict) {
        Result<SysDict> result = new Result<SysDict>();
        try {
            sysDict.setCreateTime(new Date());
            sysDict.setIsDelete("0");
            sysDictService.save(sysDict);
            result.success("添加成功！");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            result.error500("操作失败");
        }
        return result;
    }

    /**
     * 编辑
     *
     * @param sysDict
     * @return
     */
    @ApiOperation(value = "字典表-编辑", notes = "字典表-编辑")
    @PutMapping(value = "/edit")
    public Result<SysDict> edit(@RequestBody SysDict sysDict) {
        Result<SysDict> result = new Result<SysDict>();
        SysDict sysDictEntity = sysDictService.getById(sysDict.getId());
        if (sysDictEntity == null) {
            result.error500("未找到对应实体");
        } else {
            boolean ok = sysDictService.updateById(sysDict);
            //TODO 返回false说明什么？
            if (ok) {
                result.success("修改成功!");
            }
        }

        return result;
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "字典表-通过id删除", notes = "字典表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        try {
            sysDictService.removeById(id);
        } catch (Exception e) {
            log.error("删除失败", e.getMessage());
            return Result.error("删除失败!");
        }
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "字典表-批量删除", notes = "字典表-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<SysDict> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        Result<SysDict> result = new Result<SysDict>();
        if (ids == null || "".equals(ids.trim())) {
            result.error500("参数不识别！");
        } else {
            this.sysDictService.removeByIds(Arrays.asList(ids.split(",")));
            result.success("删除成功!");
        }
        return result;
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "字典表-通过id查询", notes = "字典表-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<SysDict> queryById(@RequestParam(name = "id", required = true) String id) {
        Result<SysDict> result = new Result<SysDict>();
        SysDict sysDict = sysDictService.getById(id);
        if (sysDict == null) {
            result.error500("未找到对应实体");
        } else {
            result.setResult(sysDict);
            result.setSuccess(true);
        }
        return result;
    }

}
