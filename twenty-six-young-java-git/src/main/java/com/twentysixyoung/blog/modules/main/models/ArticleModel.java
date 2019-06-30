package com.twentysixyoung.blog.modules.main.models;

import com.twentysixyoung.blog.modules.main.entity.Article;
import lombok.Data;

/**
 * This model is another result converted from Article Entity to ArticleModel aims to
 * match the VUE framework data structure requirement;
 */
@Data
public class ArticleModel {

    /** Primary key */
    private String id;
    /** The another website path at which you can access through */
    private String href;
    /** The url the computer can find your picture by for showing your portrait*/
    private String avatar;
    /** An instruction */
    private String description;
    /** The main body of the article  */
    private String content;

    /**
     * Converter
     * @param article
     * @return
     */
    public ArticleModel entityToModelConverter(Article article) {
        this.id = article.getId();
        this.avatar = article.getPicPath();
        this.description = article.getDescription();
        this.content = article.getContent();
        return this;
    }
}
