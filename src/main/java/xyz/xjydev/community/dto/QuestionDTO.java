package xyz.xjydev.community.dto;

import lombok.Data;
import xyz.xjydev.community.model.User;

/**
 * @author: 28767
 * @date: 2020/9/7 23:20
 */

@Data
public class QuestionDTO {

    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;
}
