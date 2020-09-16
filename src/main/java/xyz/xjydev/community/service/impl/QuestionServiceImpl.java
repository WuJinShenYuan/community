package xyz.xjydev.community.service.impl;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.xjydev.community.dto.PaginationDTO;
import xyz.xjydev.community.dto.QuestionDTO;
import xyz.xjydev.community.mapper.QuestionMapper;
import xyz.xjydev.community.mapper.UserMapper;
import xyz.xjydev.community.model.Question;
import xyz.xjydev.community.model.QuestionExample;
import xyz.xjydev.community.model.User;
import xyz.xjydev.community.service.QuestionService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: 28767
 * @date: 2020/9/7 22:07
 */

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    /** 把问题数据写入数据库 */
    @Override
    public void createQuestion(Question question) {
        questionMapper.insertSelective(question);
    }

    /** 输出问题数据列表
     * @param page
     * @param size
     * @return*/
    @Override
    public PaginationDTO findQuestionList(Integer page, Integer size) {
        Integer totalCount=(int) questionMapper.countByExample(new QuestionExample());
        // 有多少页
        Integer totalPage = 0;
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        // 最少第一页,最大最后一页
        if(page<1){
            page=1;
        }else if(page>totalPage){
            page=totalPage;
        }
        Integer offset= size * (page - 1);
        if(offset<0){
            offset=0;
        }
        List<Question> questionList=questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,size));

        /** 给questionDTOList写入数据 */
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questionList) {
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        /** 给paginationDTO写入数据 */
        paginationDTO.setQuestions(questionDTOList);
        paginationDTO.setPagination(totalPage,page,size);
        return paginationDTO;
    }

    /** 根据用户id查询问题列表 */
    @Override
    public PaginationDTO findQuestionListById(Integer id, Integer page, Integer size) {
        // 当前用户的问题数据数
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andCreatorEqualTo(id);
        Integer totalCountById=(int) questionMapper.countByExample(questionExample);

        // 有多少页
        Integer totalPage = 0;
        if (totalCountById % size == 0) {
            totalPage = totalCountById / size;
        } else {
            totalPage = totalCountById / size + 1;
        }

        // 最少第一页,最大最后一页
        if(page<1){
            page=1;
        }else if(page>totalPage){
            page=totalPage;
        }

        Integer offset= size * (page - 1);
        if(offset<0){
            offset=0;
        }
        QuestionExample questionExample1 = new QuestionExample();
        questionExample1.createCriteria()
                .andCreatorEqualTo(id);
        List<Question> questionList=questionMapper.selectByExampleWithRowbounds(questionExample1,new RowBounds(offset,size));

        /** 给questionDTOList写入数据 */
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questionList) {
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }

        /** 给paginationDTO写入数据 */
        paginationDTO.setQuestions(questionDTOList);
        paginationDTO.setPagination(totalPage,page,size);
        return paginationDTO;
    }

    @Override
    public QuestionDTO getById(Integer id) {

        // 根据问题id查询问题数据,并放入DTO中
        Question question= questionMapper.selectByPrimaryKey(id);
        User user=userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);

        return questionDTO;
    }

    @Override
    public void createOnUpdate(Question question) {
        if(question.getId()==null){
            // 插入
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insertSelective(question);
        }else{
            // 更新
            question.setGmtModified(System.currentTimeMillis());
            QuestionExample updateExample = new QuestionExample();
            updateExample.createCriteria()
                    .andCreatorEqualTo(question.getCreator());
            questionMapper.updateByExampleSelective(question,updateExample);
        }
    }
}
