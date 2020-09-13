package xyz.xjydev.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.xjydev.community.dto.AccessTokenDTO;
import xyz.xjydev.community.dto.GithubUser;
import xyz.xjydev.community.provider.GithubProvider;
import xyz.xjydev.community.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: 28767
 * @date: 2020/9/1 9:01
 * github登录授权控制器
 */

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.url}")
    private String redirectUrl;

    @Autowired
    private UserService userService;

    @GetMapping("/callback")
    public String classback(@RequestParam(value = "code") String code,
                            @RequestParam(value = "state") String state,
                            HttpServletRequest request,
                            HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_url(redirectUrl);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser=githubProvider.getUser(accessToken);
        // 如果成功获取到用户数据,并且数据没有缺失
        if(githubUser !=null && githubUser.getId() !=null){
            userService.createOnUpdate(response,githubUser);
        }else{
            //登录失败,重新登录
        }
        return "redirect:/";
    }

    /** 退出登录 */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        // 从session中移除用户数据
        request.getSession().removeAttribute("user");
        // 从cookie中删除token
        Cookie cookie = new Cookie("token",null);
        // 设置过期时间
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
