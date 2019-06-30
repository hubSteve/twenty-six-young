package com.twentysixyoung.blog.modules.main.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.twentysixyoung.blog.common.vo.Result;
import com.twentysixyoung.blog.modules.main.entity.Article;
import com.twentysixyoung.blog.modules.main.entity.CommentReply;
import com.twentysixyoung.blog.modules.main.entity.InformationCounter;
import com.twentysixyoung.blog.modules.main.models.SysDictItemModel;
import com.twentysixyoung.blog.modules.main.service.CommentReplyService;
import com.twentysixyoung.blog.modules.main.service.IArticleService;
import com.twentysixyoung.blog.modules.main.vo.ArticleAndCommentsVo;
import com.twentysixyoung.blog.modules.main.vo.ArticleAndTypesVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/language/java")
public class ArticleController {

    @Autowired
    private IArticleService articleService;
    @Autowired
    private CommentReplyService replyService;

    @RequestMapping("/list")
    public Result<IPage<Article>> list(Article article,
                                       @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                       @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize,
                                       HttpServletRequest request) {
        Result<IPage<Article>> result = new Result<>();
        IPage<Article> pageList = this.articleService.getArticleModelList(article, pageNo, pageSize, request);
        result.setSuccess(true);
        result.setMessage("查找成功!");
        result.setResult(pageList);
        return result;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Result<Article> add(@RequestBody Article article) {
        Result<Article> result = new Result<>();
        try {
            boolean ok = this.articleService.add(article);
            if (ok) {
                result.setSuccess(true);
                result.setMessage("您的数据添加成功");
            } else {
                throw new Exception("抱歉,添加数据失败啦!!");
            }
            return result;
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("您的数据添加失败了!!");
            return result;
        }
    }

    @RequestMapping("/update")
    public Result<Article> edit(@RequestBody ArticleAndTypesVO articleAndTypesVO,
                                HttpServletRequest request) {
        log.info(articleAndTypesVO.toString());
        Result<Article> result = new Result<>();
        Integer status = this.articleService.updateArticle(articleAndTypesVO, request);
        if (status == 100 || status == 10) {
            result.setSuccess(true);
            result.setCode(status);
        } else if (status == 1) {
            result.setSuccess(false);
            result.setCode(status);
            result.setMessage("您已经赞过了");
        } else if (status == 2) {
            result.setSuccess(false);
            result.setCode(status);
            result.setMessage("您已经踩过了");
        } else if (status == 0 || status == 4) {
            result.setSuccess(false);
            result.setCode(status);
            result.setMessage("对不起,由于系统原因,您目前无法给予评论的点赞");
        }
        return result;
    }

    @RequestMapping("/queryVisitInfoByIpOrUsername")
    public Result<List<InformationCounter>> queryVisitInfoByIpOrUsername(HttpServletRequest request) {
        Result<List<InformationCounter>> result = new Result<>();
        String username = "";
        List<InformationCounter> infoList = this.articleService.queryVisitInfoByIpOrUsername(username, request);
        result.setSuccess(true);
        result.setResult(infoList);
        return result;
    }

    @RequestMapping(value = "/queryOne", method = RequestMethod.GET)
    public Result<List<Article>> queryOne() {
        Result<List<Article>> result = new Result<>();
        result.setSuccess(true);
        result.setMessage("数据您收到了吗?");
        return result;
    }

    @RequestMapping(value = "/comments", method = RequestMethod.POST)
    public Result<?> comments(@RequestBody ArticleAndCommentsVo articleAndCommentsVo, HttpServletRequest request) {
        Result<?> result = new Result<>();
        Integer returnCode = this.articleService.comments(articleAndCommentsVo, request);
        if (returnCode == 100 || returnCode == 10) {
            result.setSuccess(true);
            result.setCode(returnCode);
            return result;
        }
        result.setSuccess(false);
        result.setCode(returnCode);
        return result;
    }

    @RequestMapping(value = "/getUUUID", method = RequestMethod.GET)
    public Result<String> getUUID() {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        Result<String> result = new Result<>();
        result.setResult(uuid);
        result.setSuccess(true);
        return result;
    }

    @RequestMapping(value = "/saveComment", method = RequestMethod.POST)
    public Result<?> saveComment(@RequestBody CommentReply commentReply) {
        boolean ok = this.replyService.saveComment(commentReply);
        Result<String> result = new Result<>();
        if (ok) {
            result.setSuccess(true);
            result.setMessage("保存成功");
            return result;
        }
        result.setSuccess(false);
        result.setMessage("保存失败!");
        return result;
    }

    @RequestMapping(value = "/queryComments", method = RequestMethod.GET)
    public Result<?> queryComments(@RequestParam(name = "id") String id) {
        List<CommentReply> commentReplyList = this.replyService.queryComments(id);
        Result<List<CommentReply>> result = new Result<>();
        result.setSuccess(true);
        result.setResult(commentReplyList);
        result.setMessage("查询成功");
        return result;
    }

    @RequestMapping(value = "getDicts", method = RequestMethod.GET)
    public Result<?> getDicts(@RequestParam(name = "dictCode") String dictCode) {
        Result<List<SysDictItemModel>> result = new Result<>();
        List<SysDictItemModel> dictList = articleService.getDictionaryDataByDictCode(dictCode);
        if (null == dictList) {
            result.setSuccess(false);
            result.setResult(new ArrayList<>());
        } else {
            result.setSuccess(true);
            result.setResult(dictList);
        }
        return result;
    }
}
