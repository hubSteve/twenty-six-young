package com.twentysixyoung.blog.modules.main.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.twentysixyoung.blog.modules.main.entity.Article;
import com.twentysixyoung.blog.modules.main.entity.InformationCounter;
import com.twentysixyoung.blog.modules.main.models.SysDictItemModel;
import com.twentysixyoung.blog.modules.main.vo.ArticleAndCommentsVo;
import com.twentysixyoung.blog.modules.main.vo.ArticleAndTypesVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


public interface IArticleService extends IService<Article> {

    /**
     * @method Insert
     * @param article
     * @return
     */
    boolean add(Article article);

    /**
     * Get the Article data from database and pageable responses to website
     * @param article
     * @param pageNo
     * @param pageSize
     * @param request
     * @return
     */
    IPage<Article> getArticleModelList(Article article, Integer pageNo, Integer pageSize,
                                         HttpServletRequest request);

    /**
     * update Article entity depends on the given primary key which being carried by parameter
     * @param articleAndTypesVO
     * @return
     */
    Integer updateArticle(ArticleAndTypesVO articleAndTypesVO, HttpServletRequest request);

    List<InformationCounter> queryVisitInfoByIpOrUsername(String username, HttpServletRequest request);

    Integer comments(ArticleAndCommentsVo articleAndCommentsVo, HttpServletRequest request);

    /**
     * get dictionary-type data
     * @param dictCode
     * @return
     */
    List<SysDictItemModel> getDictionaryDataByDictCode(String dictCode);
}
