package com.twentysixyoung.blog.modules.main.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.twentysixyoung.blog.modules.main.entity.User;
import com.twentysixyoung.blog.modules.main.mapper.LoginMapper;
import com.twentysixyoung.blog.modules.main.models.LoginModel;
import com.twentysixyoung.blog.modules.main.service.LoginService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, User> implements LoginService {

    @Override
    public User signIn(LoginModel user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionPassword = String.valueOf(session.getAttribute("username"));
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<>();
        query.eq(User::getPassword,user.getPassword());
        query.eq(User::getUsername,user.getUsername());
        User userFromDatabase = this.getOne(query);
        if(sessionPassword.equals(user.getPassword().trim())){
            return userFromDatabase;
        }else if(userFromDatabase != null){
            session.setAttribute("username",user.getPassword());
            session.setMaxInactiveInterval(6000);
            return userFromDatabase;
        }else{
            return null;
        }
    }

    @Override
    public User createAccount(User user) {
        user.setBlogLevel(1);
        user.setCommentCount(0);
        user.setLikeCount(0);
        user.setOriginalCount(0);
        user.setPosition(0);
        user.setViewCount(0);
        user.setScore(0);
        boolean ok = this.save(user);
        if(ok) return user;
        else return null;
    }
}
