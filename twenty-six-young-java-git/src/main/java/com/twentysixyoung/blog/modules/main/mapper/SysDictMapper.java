package com.twentysixyoung.blog.modules.main.mapper;

import java.util.List;

import com.twentysixyoung.blog.modules.main.entity.SysDict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: SysDict mapper interface
 * @Author: Steve
 * @Date:   2019-06-24
 * @Version: V1.0
 */
public interface SysDictMapper extends BaseMapper<SysDict> {

    /**
     * Query all the dictionary data from DB and map them each to entity {@link SysDict}
     * @return
     */
//    List<SysDict> queryAllFromSysDict();
}
