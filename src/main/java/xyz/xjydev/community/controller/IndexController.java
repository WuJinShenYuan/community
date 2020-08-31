package xyz.xjydev.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: 28767
 * @date: 2020/8/30 23:16
 */

@Controller
public class IndexController {

    /** 到达首页 */
    @GetMapping("/")
    public String hello(){
        return "index";
    }
}
