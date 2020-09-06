package xyz.xjydev.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import xyz.xjydev.community.model.Question;

/**
 * @author: 28767
 * @date: 2020/9/3 21:47
 * question数据访问层
 */

@Mapper
@Repository
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag)" +
            " values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);
}
