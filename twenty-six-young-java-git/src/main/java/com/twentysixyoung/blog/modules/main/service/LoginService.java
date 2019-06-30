package com.twentysixyoung.blog.modules.main.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.twentysixyoung.blog.modules.main.entity.User;
import com.twentysixyoung.blog.modules.main.models.LoginModel;

import javax.servlet.http.HttpServletRequest;

public interface LoginService extends IService<User>{

    /**
     * Sign in
     * @return
     */
    User signIn(LoginModel user, HttpServletRequest request);

    /**
     * @Description Create an account
     * @param user
     * @return
     */
    User createAccount(User user);
}
