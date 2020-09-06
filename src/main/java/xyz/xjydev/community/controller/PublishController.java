package xyz.xjydev.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.xjydev.community.mapper.QuestionMapper;
import xyz.xjydev.community.model.Question;
import xyz.xjydev.community.model.User;
import xyz.xjydev.community.service.UserService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: 28767
 * @date: 2020/9/2 23:33
 * publish页面控制器
 */

@Controller
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserService userService;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag,
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
        User user=userService.FindUserByCookie(request);

        // 以后可以设置为只有登录了才能访问该页面
        if (user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        question.setTitle(title);
        question.setDescription(description);
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        question.setCreator(user.getId());
        question.setTag(tag);
        questionMapper.create(question);
        return "redirect:/";
    }
}
