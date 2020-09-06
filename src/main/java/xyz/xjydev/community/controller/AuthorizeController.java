package xyz.xjydev.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.xjydev.community.dto.AccessTokenDTO;
import xyz.xjydev.community.dto.GithubUser;
import xyz.xjydev.community.model.User;
import xyz.xjydev.community.provider.GithubProvider;
import xyz.xjydev.community.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

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
        if(githubUser !=null && githubUser.getId() !=null){
            // 把用户数据写入数据库
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userService.insertUser(user);
            response.addCookie(new Cookie("token",token));
            // 登录成功,写cookie和session
            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else{
            //登录失败,重新登录
            return "redirect:/";
        }
    }
}
