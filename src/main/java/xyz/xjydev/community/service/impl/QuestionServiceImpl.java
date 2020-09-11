package xyz.xjydev.community.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.xjydev.community.dto.PaginationDTO;
import xyz.xjydev.community.dto.QuestionDTO;
import xyz.xjydev.community.mapper.QuestionMapper;
import xyz.xjydev.community.mapper.UserMapper;
import xyz.xjydev.community.model.Question;
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
        questionMapper.createQuestion(question);
    }

    /** 输出问题数据列表
     * @param page
     * @param size
     * @return*/
    @Override
    public PaginationDTO findQuestionList(Integer page, Integer size) {
        Integer totalCount=questionMapper.selectTotal();
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
        List<Question> questionList=questionMapper.findQuestionList(offset,size);

        /** 给questionDTOList写入数据 */
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questionList) {
            User user=userMapper.findById(question.getCreator());
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
         Integer totalCountById =questionMapper.selectTotalById(id);

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
        List<Question> questionList=questionMapper.findQuestionListById(id,offset,size);

        /** 给questionDTOList写入数据 */
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questionList) {
            User user=userMapper.findById(question.getCreator());
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
        Question question= questionMapper.findById(id);
        User user=userMapper.findById(question.getCreator());
        QuestionDTO questionDTO=new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);

        return questionDTO;
    }
}
