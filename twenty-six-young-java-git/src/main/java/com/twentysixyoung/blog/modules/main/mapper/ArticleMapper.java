package com.twentysixyoung.blog.modules.main.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.twentysixyoung.blog.modules.main.entity.Article;

public interface ArticleMapper extends BaseMapper<Article> {
//
//    /**
//     * Base on thes given params either ip or username to query information
//     * @param ip
//     * @param username
//     * @return
//     */
//    public List<InformationCounter> queryVisitInfoByIpOrUsername(@Param("ip")String ip, @Param("username") String username);
//
//    /**
//     * Base on the given ip value to count its quantity in database
//     * @param ip
//     * @return
//     */
//    @Select("SELECT COUNT(${fieldName}) FROM information_counter where ip = #{ip} and java_id = #{javaId}")
//    Integer countByIpAndJavaId(String ip, String javaId, String fieldName);
}
