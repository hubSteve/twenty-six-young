package com.twentysixyoung.blog.modules.main.entity;

import java.io.Serializable;
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

/**
 * @Description: 字典值表
 * @Author: jeecg-boot
 * @Date:   2019-06-25
 * @Version: V1.0
 */
@Data
@TableName("sys_dict_item")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="sys_dict_item对象", description="字典值表")
public class SysDictItem implements Serializable{

    private static final long serialVersionUid = 1L;

	/**id*/
	@TableId(type = IdType.UUID)
    @ApiModelProperty(value = "id")
	private java.lang.String id;
	/**dictId*/
    @ApiModelProperty(value = "dictId")
	private java.lang.String dictId;
	/**itemText*/
    @ApiModelProperty(value = "itemText")
	private java.lang.String itemText;
	/**itemValue*/
    @ApiModelProperty(value = "itemValue")
	private java.lang.String itemValue;
	/**sortOrder*/
    @ApiModelProperty(value = "sortOrder")
	private java.lang.String sortOrder;
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
	/**status*/
    @ApiModelProperty(value = "status")
	private java.lang.String status;
	/**description*/
    @ApiModelProperty(value = "description")
	private java.lang.String description;
}
