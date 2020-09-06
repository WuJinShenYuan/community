package xyz.xjydev.community.model;

import lombok.Data;

/**
 * @author: 28767
 * @date: 2020/9/3 21:48
 * question实体类
 */

@Data
public class Question {

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
}
