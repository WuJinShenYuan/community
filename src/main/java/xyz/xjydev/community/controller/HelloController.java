package xyz.xjydev.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: 28767
 * @date: 2020/8/30 23:16
 */

@Controller
public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name") String name, Model model){
       model.addAttribute("name",name);
        return "hello";
    }
}
