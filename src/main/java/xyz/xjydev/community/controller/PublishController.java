package xyz.xjydev.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.xjydev.community.dto.QuestionDTO;
import xyz.xjydev.community.model.Question;
import xyz.xjydev.community.model.User;
import xyz.xjydev.community.service.QuestionService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: 28767
 * @date: 2020/9/2 23:33
 * publish页面控制器
 */

@Controller
public class PublishController {

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String toPublish(){
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String edit(@PathVariable("id") Integer id,
                       Model model){
        QuestionDTO questionDTO=questionService.getById(id);
        model.addAttribute("id",id);
        model.addAttribute("title",questionDTO.getTitle());
        model.addAttribute("description",questionDTO.getDescription());
        model.addAttribute("tag",questionDTO.getTag());
        return "publish";
    }

    @PostMapping("/publish")
    public String publish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
                            @RequestParam("id") Integer id,
                            HttpServletRequest request,
                            Model model){
        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        if(title ==null || title ==""){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if(description ==null || description ==""){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if(tag ==null || tag ==""){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        Question question =new Question();
        User user=(User) request.getSession().getAttribute("user");

        // 以后可以设置为只有登录了才能访问该页面
        if (user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        question.setId(id);
        question.setTitle(title);
        question.setDescription(description);
        question.setCreator(user.getId());
        question.setTag(tag);
        questionService.createOnUpdate(question);
        return "redirect:/";
    }
}
