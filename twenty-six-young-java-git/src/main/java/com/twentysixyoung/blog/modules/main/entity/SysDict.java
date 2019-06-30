package com.twentysixyoung.blog.modules.main.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 字典表
 * @Author: jeecg-boot
 * @Date:   2019-06-24
 * @Version: V1.0
 */
@Data
@TableName("sys_dict")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="sys_dict对象", description="字典表")
public class SysDict implements Serializable{
    private static final long serialVersionUid = 1L;
	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private java.lang.String id;
	/**dictCode*/
    @ApiModelProperty(value = "dictCode")
	private java.lang.String dictCode;
	/**dictName*/
    @ApiModelProperty(value = "dictName")
	private java.lang.String dictName;
	/**description*/
    @ApiModelProperty(value = "description")
	private java.lang.String description;
	/**isDelete*/
    @ApiModelProperty(value = "isDelete")
	private java.lang.String isDelete;
	/**createTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "createTime")
	private java.util.Date createTime;
	/**updateTime*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "updateTime")
	private java.util.Date updateTime;
}
