package com.twentysixyoung.blog.modules.main.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("user")
public class User implements Serializable {

    private static final long serialVersionUid = 1L;

    @TableId(type = IdType.UUID)
    /**Primary key of this table*/
    private String id;

    /**user's portrait*/
    private String avatar;

    /**user's username to login*/
    private String username;

    /**the password matches that username*/
    private String password;

    /**user's age*/
    private Integer age;

    /**user's real name*/
    private String realName;

    /**user's email*/
    private String email;

    /**user's living address*/
    private String address;

    /**the time to recorded when a user born*/
    private Date birthday;

    /**the sex features man and women*/
    private String gender;

    /**the status features a user's account whether normal*/
    private String status;

    /**the time recorded when a user create his(her) account */
    private Date createTime;

    /**the time recorded when a user update his(her) account*/
    private Date updateTime;

    /**the user's previous password used to find his(she)password back when he(she) forgotten the new one*/
    private Date previousPassword;

    private String phone;

    private int blogLevel;

    private int position;

    private int score;

    private int likeCount;

    private int commentCount;

    private int viewCount;

    private int originalCount;

}
