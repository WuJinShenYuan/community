package xyz.xjydev.community.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.xjydev.community.dto.GithubUser;
import xyz.xjydev.community.mapper.UserMapper;
import xyz.xjydev.community.model.User;
import xyz.xjydev.community.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author: 28767
 * @date: 2020/9/4 0:08
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void insertUser(User user) {
        userMapper.insertUser(user);
    }

    @Override
    public User findByToken(String token) {
        return userMapper.findByToken(token);
    }

    @Override
    public User FindUserByCookie(HttpServletRequest httpServletRequest) {
        // 查看cookie中是否有数据库中保存的token,通过token获取用户数据
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies !=null && cookies.length !=0){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token=cookie.getValue();
                    User user=userMapper.findByToken(token);
                    if(user !=null){
                        if(httpServletRequest.getSession().getAttribute("user")==null){
                            httpServletRequest.getSession().setAttribute("user",user);
                        }
                        return user;
                    }
                    break;
                }
            }
        }
        return null;
    }

    @Override
    public void createOnUpdate(HttpServletResponse response, GithubUser githubUser) {
        User dbUser=userMapper.findByAccountId(String.valueOf(githubUser.getId()));
        System.out.println("成功");
        // 判断数据库是否已经有user数据
        if(dbUser==null){
            // 插入
            System.out.println("插入");
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setName(githubUser.getName());
            user.setToken(token);
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setBio(githubUser.getBio());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            response.addCookie(new Cookie("token",token));
            userMapper.insertUser(user);
//            request.getSession().setAttribute("user",user);
        }else{
            // 更新
            System.out.println("更新");
            dbUser.setName(githubUser.getName());
            dbUser.setToken(UUID.randomUUID().toString());
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setBio(githubUser.getBio());
            dbUser.setAvatarUrl(githubUser.getAvatarUrl());
            userMapper.updateUser(dbUser);
        }
    }
}
