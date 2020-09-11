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
 * user数据访问层
 */

@Mapper
@Repository
public interface UserMapper {

    /** 插入User数据 */
    @Insert("insert into user (account_id,name,token,gmt_create,gmt_modified,bio,avatar_url)" +
            " values (#{accountId},#{name},#{token},#{gmtCreate},#{gmtModified},#{bio},#{avatarUrl})")
    void insertUser(User user);

    /** 根据token查询User数据 */
    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);

    /** 根据id查询用户数据 */
    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer id);
}
