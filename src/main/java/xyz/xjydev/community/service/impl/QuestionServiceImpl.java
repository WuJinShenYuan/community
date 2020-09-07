package xyz.xjydev.community.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    /** 输出问题数据列表 */
    @Override
    public List<QuestionDTO> findQuestionList() {
        List<Question> questionList=questionMapper.findQuestionList();
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for (Question question : questionList) {
            User user=userMapper.findById(question.getCreator());
            QuestionDTO questionDTO=new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        return questionDTOList;
    }
}
