package xyz.xjydev.community.service;

import org.apache.ibatis.annotations.Param;
import xyz.xjydev.community.model.User;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: 28767
 * @date: 2020/9/4 0:07
 */

public interface UserService {

    void insertUser(User user);

    User findByToken(@Param("token") String token);

    User FindUserByCookie(HttpServletRequest httpServletRequest);
}
