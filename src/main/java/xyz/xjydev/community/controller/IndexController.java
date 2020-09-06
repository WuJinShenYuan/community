package xyz.xjydev.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.xjydev.community.model.User;
import xyz.xjydev.community.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author: 28767
 * @date: 2020/8/30 23:16
 * index页面控制器
 */

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    /** 到达首页 */
    @GetMapping("/")
    public String hello(HttpServletRequest request){
        // 查看cookie中是否有数据库中保存的token,通过token获取用户数据
        userService.FindUserByCookie(request);
        return "index";
    }
}
