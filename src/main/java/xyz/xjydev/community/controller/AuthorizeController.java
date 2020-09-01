package xyz.xjydev.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xyz.xjydev.community.dto.AccessTokenDTO;
import xyz.xjydev.community.dto.GithubUser;
import xyz.xjydev.community.provider.GithubProvider;

/**
 * @author: 28767
 * @date: 2020/9/1 9:01
 */

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String classback(@RequestParam(value = "code") String code,
                            @RequestParam(value = "state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("d02bb3f971ea9947d521");
        accessTokenDTO.setClient_secret("2ccb688fbe42a38b83e38ef854d0bcd9727a107d");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_url("http://localhost:8887/callback");
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user=githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}
