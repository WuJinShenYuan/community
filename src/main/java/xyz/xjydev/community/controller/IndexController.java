package xyz.xjydev.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.xjydev.community.dto.PaginationDTO;
import xyz.xjydev.community.service.QuestionService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: 28767
 * @date: 2020/8/30 23:16
 * index页面控制器
 */

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    /** 到达首页 */
    @GetMapping("/")
    public String toIndex(HttpServletRequest request,
                        Model model,
                        @RequestParam(value = "page",defaultValue = "1") Integer page,
                        @RequestParam(value = "size",defaultValue = "5") Integer size
                        ){
        // 查询出所有问题列表
        PaginationDTO paginations=questionService.findQuestionList(page,size);
        model.addAttribute("paginations",paginations);
        return "index";
    }


}
