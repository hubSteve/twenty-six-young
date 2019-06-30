package com.twentysixyoung.blog.modules.main.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.twentysixyoung.blog.common.utils.QueryGenerator;
import com.twentysixyoung.blog.common.vo.ReturnCodes;
import com.twentysixyoung.blog.common.vo.TimeUtils;
import com.twentysixyoung.blog.modules.main.entity.*;
import com.twentysixyoung.blog.modules.main.mapper.ArticleMapper;
import com.twentysixyoung.blog.modules.main.models.SysDictItemModel;
import com.twentysixyoung.blog.modules.main.service.*;
import com.twentysixyoung.blog.modules.main.vo.ArticleAndCommentsVo;
import com.twentysixyoung.blog.modules.main.vo.ArticleAndTypesVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {
    @Autowired
    private InformationCounterService informationCounterService;
    @Autowired
    private CommentReplyService commentReplyService;
    @Autowired
    private ISysDictItemService sysDictItemService;
    @Resource
    private RedisTemplate redisTemplate;
    @Autowired
    private ISysDictService sysDictService;

    /**
     * Insert the article param into database
     *
     * @param article
     * @return
     */
    @Override
    public boolean add(Article article) {
        try {
            article.setCreateTime(new Date());
            article.setDescription(article.getDescription());
            return this.save(article);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * Get the Article data from database pageable return to service layer
     *
     * @param article
     * @param pageNo
     * @param pageSize
     * @param request
     * @return
     */
    @Override
    public IPage<Article> getArticleModelList(Article article, Integer pageNo, Integer pageSize,
                                              HttpServletRequest request) {
        QueryWrapper<Article> queryWrapper = QueryGenerator.initQueryWrapper(article, request.getParameterMap());
        Page<Article> page = new Page<>();
        IPage<Article> pageList = this.page(page, queryWrapper);
        return pageList;
    }


    /**
     * @param articleAndTypesVO
     * @return
     */
    @Override
    public Integer updateArticle(ArticleAndTypesVO articleAndTypesVO, HttpServletRequest request) {
        // 请求的ip地址
        String requestIp = request.getHeader("Origin");
        Article article = articleAndTypesVO.getArticle();
        String type = articleAndTypesVO.getType();
        switch (type) {
            case "like":
                return this.likeCounter(article, requestIp);
            case "dislike":
                return this.dislikeCounter(article, requestIp);
            case "visit":
                return this.saveOrUpdateInformationCounter(article, requestIp);
            default:
                return ReturnCodes.UPDATE_FAILED;
        }
    }

    private Integer likeCounter(Article article, String requestIp) {
        LambdaQueryWrapper<InformationCounter> query = new LambdaQueryWrapper<>();
        query.eq(InformationCounter::getIp, requestIp);
        query.eq(InformationCounter::getArticleId, article.getId());
        int likeCount = this.informationCounterService.count(query);
        InformationCounter informationCounterA = this.informationCounterService.getOne(query);
        if (likeCount != 0) {
            query.eq(InformationCounter::getLikeUpdateTime, TimeUtils.getTimeWithNoHMS());
            InformationCounter informationCounterB = this.informationCounterService.getOne(query);
            if (informationCounterB == null) {
                int originLikeCount = informationCounterA.getLikeCount();
                originLikeCount++;
                informationCounterA.setLikeCount(originLikeCount);
                informationCounterA.setLikeUpdateTime(TimeUtils.getTimeWithNoHMS());
                this.informationCounterService.updateById(informationCounterA);
                int likeQuantity = article.getLikeCount();
                likeQuantity++;
                article.setLikeCount(likeQuantity);
                this.updateById(article);
                return ReturnCodes.UPDATE_SUCCESSFUL;
            }
            return ReturnCodes.USER_ALREADY_LIKE;
        }
        InformationCounter newInformationCounter = new InformationCounter();
        newInformationCounter.setLikeCount(1);
        newInformationCounter.setIp(requestIp);
        newInformationCounter.setLikeUpdateTime(TimeUtils.getTimeWithNoHMS());
        newInformationCounter.setArticleId(article.getId());
        int likeQuantity = article.getLikeCount();
        likeQuantity++;
        article.setLikeCount(likeQuantity);
        this.updateById(article);
        this.informationCounterService.save(newInformationCounter);
        return ReturnCodes.SAVE_SUCCESSFUL;

    }

    private Integer dislikeCounter(Article article, String requestIp) {
        LambdaQueryWrapper<InformationCounter> query = new LambdaQueryWrapper<>();
        query.eq(InformationCounter::getIp, requestIp);
        query.eq(InformationCounter::getArticleId, article.getId());
        int likeCount = this.informationCounterService.count(query);
        InformationCounter informationCounterA = this.informationCounterService.getOne(query);
        if (likeCount != 0) {
            query.eq(InformationCounter::getLikeUpdateTime, TimeUtils.getTimeWithNoHMS());
            InformationCounter informationCounterB = this.informationCounterService.getOne(query);
            if (informationCounterB == null) {
                int originDislikeCount = informationCounterA.getDislikeCount();
                originDislikeCount++;
                informationCounterA.setDislikeCount(originDislikeCount);
                informationCounterA.setLikeUpdateTime(TimeUtils.getTimeWithNoHMS());
                this.informationCounterService.updateById(informationCounterA);
                int dislikeQuantity = article.getDislikeCount();
                dislikeQuantity++;
                article.setDislikeCount(dislikeQuantity);
                this.updateById(article);
                return ReturnCodes.UPDATE_SUCCESSFUL;
            }
            return ReturnCodes.USER_ALREADY_DISLIKE;
        }
        InformationCounter newInformationCounter = new InformationCounter();
        newInformationCounter.setIp(requestIp);
        newInformationCounter.setDislikeCount(1);
        newInformationCounter.setLikeUpdateTime(TimeUtils.getTimeWithNoHMS());
        newInformationCounter.setArticleId(article.getId());
        this.informationCounterService.save(newInformationCounter);
        int dislikeCount = article.getDislikeCount();
        dislikeCount++;
        article.setDislikeCount(dislikeCount);
        this.updateById(article);
        return ReturnCodes.SAVE_SUCCESSFUL;

    }

    private Integer saveOrUpdateInformationCounter(Article article, String requestIp) {
        LambdaQueryWrapper<InformationCounter> query = new LambdaQueryWrapper<>();
        query.eq(InformationCounter::getIp, requestIp);
        query.eq(InformationCounter::getArticleId, article.getId());
        InformationCounter informationCounter = this.informationCounterService.getOne(query);
        if (informationCounter == null) {
            InformationCounter newInformationCounter = new InformationCounter();
            newInformationCounter.setVisitCount(1);
            newInformationCounter.setIp(requestIp);
            newInformationCounter.setArticleId(article.getId());
            this.informationCounterService.save(newInformationCounter);
            int viewCount = article.getViewCount();
            viewCount++;
            article.setViewCount(viewCount);
            this.updateById(article);
            return ReturnCodes.UPDATE_SUCCESSFUL;
        }
        int visitCount = informationCounter.getVisitCount();
        visitCount++;
        informationCounter.setVisitCount(visitCount);
        int viewCount = article.getViewCount();
        viewCount++;
        article.setViewCount(viewCount);
        this.updateById(article);
        this.informationCounterService.updateById(informationCounter);
        return ReturnCodes.UPDATE_SUCCESSFUL;
    }

    @Override
    public List<InformationCounter> queryVisitInfoByIpOrUsername(String username, HttpServletRequest request) {
        String ip = request.getHeader("Origin");
        LambdaQueryWrapper<InformationCounter> query = new LambdaQueryWrapper<>();
        if (username.trim().equals("") || StringUtils.isEmpty(username)) {
            query.eq(InformationCounter::getIp, ip);
        } else {
            query.eq(InformationCounter::getUpdateAuthor, username);
        }
        return this.informationCounterService.list(query);
    }

    @Override
    public Integer comments(ArticleAndCommentsVo articleAndCommentsVo, HttpServletRequest request) {
        // The request IP address
        String requestIp = request.getHeader("Origin");
        try {
            Article article = articleAndCommentsVo.getArticle();
            CommentReply commentReply = articleAndCommentsVo.getCommentReply();
            if (commentReply.getId() == null) {
                commentReply.setCommentator(requestIp);
                this.commentReplyService.save(commentReply);
                this.updateById(article);
                return ReturnCodes.SAVE_SUCCESSFUL;
            }
            this.updateById(article);
            commentReply.setCommentator(requestIp);
            this.commentReplyService.updateById(commentReply);
            return ReturnCodes.UPDATE_SUCCESSFUL;
        } catch (NullPointerException n) {
            n.fillInStackTrace();
            return ReturnCodes.SAVE_FAILED;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public synchronized List<SysDictItemModel> getDictionaryDataByDictCode(String dictCode) {
        String dictionary = null;
        List<SysDictItemModel> redisList = new ArrayList<>();
        try {
//            redisTemplate.setValueSerializer(new GenericToStringSerializer<String>(String.class));
            dictionary = (String) redisTemplate.boundValueOps(dictCode).get();
            if (null == dictionary) {
                List<SysDict> dictList = sysDictService.list();
                List<SysDictItem> dictItemList = sysDictItemService.list();
                if (null != dictList && dictList.size() > 0) {
                    dictList.forEach(s -> {
                        if (dictCode.equals(s.getDictCode()))
                        iterateDictItemList(dictItemList, redisList, s.getId(), dictCode);
                    });
                    return redisList;
                }
                return null;
            } else {
                List<SysDictItemModel> list = JSON.parseArray(dictionary, SysDictItemModel.class);
                return list;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e.getCause());
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public void iterateDictItemList(List<SysDictItem> list, List<SysDictItemModel> redisList,
                                    String dictId, String dictCode) {
        try {
            if (null != list && list.size() > 0) {
                list.forEach(s -> {
                    if (s.getDictId().equals(dictId))
                        redisList.add(new SysDictItemModel().convertToModel(s));
                });
                redisTemplate.boundValueOps(dictCode).set(JSONObject.toJSONString(redisList));
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            log.error(e.getMessage(), e.getCause());
        }
    }
}