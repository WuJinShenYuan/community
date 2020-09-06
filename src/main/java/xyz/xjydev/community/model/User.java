package xyz.xjydev.community.model;

import lombok.Data;

/**
 * @author: 28767
 * @date: 2020/9/1 22:06
 * user实体类
 */

@Data
public class User {

    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String bio;
    private String avatarUrl;
}
