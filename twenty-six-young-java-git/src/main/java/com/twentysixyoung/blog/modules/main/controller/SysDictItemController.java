package com.twentysixyoung.blog.modules.main.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;

import com.twentysixyoung.blog.common.utils.QueryGenerator;
import com.twentysixyoung.blog.common.vo.Result;
import com.twentysixyoung.blog.modules.main.entity.SysDictItem;
import com.twentysixyoung.blog.modules.main.service.ISysDictItemService;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 字典值表
 * @Author: jeecg-boot
 * @Date:   2019-06-25
 * @Version: V1.0
 */
@Slf4j
@Api(tags="字典值表")
@RestController
@RequestMapping("/main/sysDictItem")
public class SysDictItemController {
	@Autowired
	private ISysDictItemService sysDictItemService;
	
	/**
	  * 分页列表查询
	 * @param dictId
	 * @return
	 */
	@ApiOperation(value="字典值表-分页列表查询", notes="字典值表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<SysDictItem>> queryPageList(@RequestParam(name = "dictId") String dictId) {
		Result<IPage<SysDictItem>> result = new Result<>();
		QueryWrapper<SysDictItem> queryWrapper = new QueryWrapper<>();
		queryWrapper.eq("dict_id", dictId);
		Page<SysDictItem> page = new Page<SysDictItem>(1, 20);
		IPage<SysDictItem> pageList = sysDictItemService.page(page, queryWrapper);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}
	
	/**
	  *   添加
	 * @param sysDictItem
	 * @return
	 */
	@ApiOperation(value="字典值表-添加", notes="字典值表-添加")
	@PostMapping(value = "/add")
	public Result<SysDictItem> add(@RequestBody SysDictItem sysDictItem) {
		Result<SysDictItem> result = new Result<SysDictItem>();
		try {
            sysDictItem.setCreateTime(new Date());
            sysDictItem.setStatus("0"); // 表示未删除
			sysDictItemService.save(sysDictItem);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}
	
	/**
	  *  编辑
	 * @param sysDictItem
	 * @return
	 */
	@ApiOperation(value="字典值表-编辑", notes="字典值表-编辑")
	@PutMapping(value = "/edit")
	public Result<SysDictItem> edit(@RequestBody SysDictItem sysDictItem) {
		Result<SysDictItem> result = new Result<SysDictItem>();
		SysDictItem sysDictItemEntity = sysDictItemService.getById(sysDictItem.getId());
		if(sysDictItemEntity==null) {
			result.error500("未找到对应实体");
		}else {
			boolean ok = sysDictItemService.updateById(sysDictItem);
			//TODO 返回false说明什么？
			if(ok) {
				result.success("修改成功!");
			}
		}
		
		return result;
	}
	
	/**
	  *   通过id删除
	 * @param id
	 * @return
	 */
	@ApiOperation(value="字典值表-通过id删除", notes="字典值表-通过id删除")
	@DeleteMapping(value = "/delete")
	public Result<?> delete(@RequestParam(name="id",required=true) String id) {
		try {
			sysDictItemService.removeById(id);
		} catch (Exception e) {
			log.error("删除失败",e.getMessage());
			return Result.error("删除失败!");
		}
		return Result.ok("删除成功!");
	}
	
	/**
	  *  批量删除
	 * @param ids
	 * @return
	 */
	@ApiOperation(value="字典值表-批量删除", notes="字典值表-批量删除")
	@DeleteMapping(value = "/deleteBatch")
	public Result<SysDictItem> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		Result<SysDictItem> result = new Result<SysDictItem>();
		if(ids==null || "".equals(ids.trim())) {
			result.error500("参数不识别！");
		}else {
			this.sysDictItemService.removeByIds(Arrays.asList(ids.split(",")));
			result.success("删除成功!");
		}
		return result;
	}
	
	/**
	  * 通过id查询
	 * @param id
	 * @return
	 */
	@ApiOperation(value="字典值表-通过id查询", notes="字典值表-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<SysDictItem> queryById(@RequestParam(name="id",required=true) String id) {
		Result<SysDictItem> result = new Result<SysDictItem>();
		SysDictItem sysDictItem = sysDictItemService.getById(id);
		if(sysDictItem==null) {
			result.error500("未找到对应实体");
		}else {
			result.setResult(sysDictItem);
			result.setSuccess(true);
		}
		return result;
	}

}
