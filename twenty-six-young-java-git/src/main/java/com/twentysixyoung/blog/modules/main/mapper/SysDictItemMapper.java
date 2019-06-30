package com.twentysixyoung.blog.modules.main.mapper;

import java.util.List;

import com.twentysixyoung.blog.modules.main.entity.SysDictItem;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: a list of data which can be grouped by its dict_id,see details
 *                here {@link SysDictItem}, and each of its dict_id correspondents
 *                to the id of the {@link com.twentysixyoung.blog.modules.main.entity.SysDict}
 *                entity.
 * @Author: Steve
 * @Date:   2019-06-25
 * @Version: V1.0
 */
public interface SysDictItemMapper extends BaseMapper<SysDictItem> {

    /**
     * query all the data from {@link SysDictItem} table
     * @return
     */
//    List<SysDictItem> queryAllDataFromSysDictItem ();
}
