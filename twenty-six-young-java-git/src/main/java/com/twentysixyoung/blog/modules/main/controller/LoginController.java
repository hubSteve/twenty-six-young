package com.twentysixyoung.blog.modules.main.controller;

import com.twentysixyoung.blog.modules.main.entity.User;
import com.twentysixyoung.blog.modules.main.models.LoginModel;
import com.twentysixyoung.blog.modules.main.service.LoginService;
import com.twentysixyoung.blog.common.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/common/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/loginIn", method = RequestMethod.POST)
    public Result<User> signIn(@RequestBody LoginModel loginModel, HttpServletRequest request){
        Result result = new Result();
        User returnedUser = this.loginService.signIn(loginModel, request);
        if(returnedUser != null){
            result.setSuccess(true);
            result.setResult(returnedUser);
            result.setMessage("恭喜您登陆成功! 🙂");
            return result;
        }
        result.setSuccess(false);
        result.setMessage("登陆失败! 😒");
        return result;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result<?> createAccount(@RequestBody User user){
        Result result = new Result();
        User returnedUser = this.loginService.createAccount(user);
        if(returnedUser != null){
            result.setSuccess(true);
            result.setMessage("恭喜您,注册成功,您对我们的支持是我们前进最大动力😊😊😊😊😊...");
            result.setResult(returnedUser);
            return result;
        }
        result.setSuccess(false);
        result.setMessage("很抱歉, 注册遇到了问题😭");
        return result;
    }
}
