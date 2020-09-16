package xyz.xjydev.community.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.xjydev.community.dto.GithubUser;
import xyz.xjydev.community.mapper.UserMapper;
import xyz.xjydev.community.model.User;
import xyz.xjydev.community.model.UserExample;
import xyz.xjydev.community.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
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

//    @Override
//    public void insertUser(User user) {
//        userMapper.insertUser(user);
//    }
//
//    @Override
//    public User findByToken(String token) {
//        return userMapper.findByToken(token);
//    }

    @Override
    public User FindUserByCookie(HttpServletRequest httpServletRequest) {
        // 查看cookie中是否有数据库中保存的token,通过token获取用户数据
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies !=null && cookies.length !=0){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token=cookie.getValue();
                    UserExample userExample = new UserExample();
                    userExample.createCriteria()
                            .andTokenEqualTo(token);
                    List<User> users=userMapper.selectByExample(userExample);
                    if(users.size() !=0){
                        if(httpServletRequest.getSession().getAttribute("user")==null){
                            httpServletRequest.getSession().setAttribute("user",users.get(0));
                        }
                        return users.get(0);
                    }
                    break;
                }
            }
        }
        return null;
    }

    /** 插入或更新user数据 */
    @Override
    public void createOnUpdate(HttpServletResponse response, GithubUser githubUser) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andAccountIdEqualTo(String.valueOf(githubUser.getId()));
        List<User> users=userMapper.selectByExample(userExample);
        System.out.println("成功");
        // 判断数据库是否已经有user数据
        if(users.size()==0){
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
            userMapper.insert(user);
//            request.getSession().setAttribute("user",user);
        }else{
            // 更新
            System.out.println("更新");
            User dbUser=users.get(0);
            dbUser.setName(githubUser.getName());
            dbUser.setToken(UUID.randomUUID().toString());
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setBio(githubUser.getBio());
            dbUser.setAvatarUrl(githubUser.getAvatarUrl());
            UserExample userExample1 = new UserExample();
            userExample1.createCriteria()
                    .andAccountIdEqualTo(String.valueOf(githubUser.getId()));
            userMapper.updateByExampleSelective(dbUser, userExample1);
        }
    }
}
