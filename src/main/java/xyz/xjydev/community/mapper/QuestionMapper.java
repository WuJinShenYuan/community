package xyz.xjydev.community.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import xyz.xjydev.community.model.Question;

import java.util.List;

/**
 * @author: 28767
 * @date: 2020/9/3 21:47
 * question数据访问层
 */

@Mapper
@Repository
public interface QuestionMapper {

    /** 插入问题数据 */
    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag)" +
            " values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void createQuestion(Question question);

    /** 分页查询问题数据 */
    @Select("select * from question limit #{offset}, #{size}")
    List<Question> findQuestionList(@Param("offset") Integer offset,@Param("size") Integer size);

    /** 根据id分页查询问题数据 */
    @Select("select * from question where creator=#{creator} limit #{offset}, #{size} ")
    List<Question> findQuestionListById(@Param("creator") Integer id,@Param("offset") Integer offset,@Param("size") Integer size);

    /** 查询有多少条数据 */
    @Select("select count(*) from question")
    Integer selectTotal();

    /** 根据id查询有多少条数据 */
    @Select("select count(*) from question where creator=#{creator}")
    Integer selectTotalById(@Param("creator") Integer id);

    /** 根据问题id查询问题数据 */
    @Select("select * from question where id=#{id}")
    Question findById(@Param("id") Integer id);

    /** 根据问题id更新问题数据 */
    @Update("update question set title=#{title},description=#{description},gmt_modified=#{gmtModified},tag=#{tag} where id=#{id}")
    void updateQuestion(Question question);
}
