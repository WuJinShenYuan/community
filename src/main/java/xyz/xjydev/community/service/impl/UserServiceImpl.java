package xyz.xjydev.community.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.xjydev.community.mapper.UserMapper;
import xyz.xjydev.community.model.User;
import xyz.xjydev.community.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

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
}
