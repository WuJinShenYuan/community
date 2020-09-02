package xyz.xjydev.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import xyz.xjydev.community.model.User;

/**
 * @author: 28767
 * @date: 2020/9/1 21:57
 */

@Mapper
@Repository
public interface UserMapper {

    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified)" +
            " values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);
}
