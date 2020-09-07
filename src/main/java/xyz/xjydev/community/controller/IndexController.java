package xyz.xjydev.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import xyz.xjydev.community.dto.QuestionDTO;
import xyz.xjydev.community.model.Question;
import xyz.xjydev.community.model.User;
import xyz.xjydev.community.service.QuestionService;
import xyz.xjydev.community.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author: 28767
 * @date: 2020/8/30 23:16
 * index页面控制器
 */

@Controller
public class IndexController {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    /** 到达首页 */
    @GetMapping("/")
    public String hello(HttpServletRequest request, Model model){
        // 查看cookie中是否有数据库中保存的token,通过token获取用户数据
        userService.FindUserByCookie(request);
        // 查询出所有问题列表
        List<QuestionDTO> questionDTOList=questionService.findQuestionList();
        model.addAttribute("questionDTOList",questionDTOList);
        return "index";
    }
}
