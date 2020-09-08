package xyz.xjydev.community.service;

import xyz.xjydev.community.dto.PaginationDTO;
import xyz.xjydev.community.dto.QuestionDTO;
import xyz.xjydev.community.model.Question;

import java.util.List;

/**
 * @author: 28767
 * @date: 2020/9/7 22:06
 */

public interface QuestionService {

    void createQuestion(Question question);

    PaginationDTO findQuestionList(Integer tage, Integer size);
}
