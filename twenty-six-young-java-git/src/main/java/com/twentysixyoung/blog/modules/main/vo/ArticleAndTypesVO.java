package com.twentysixyoung.blog.modules.main.vo;

import com.twentysixyoung.blog.modules.main.entity.Article;
import lombok.Data;

@Data
public class ArticleAndTypesVO {

    private Article article;

    private String type;

}
