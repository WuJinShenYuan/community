package xyz.xjydev.community.dto;

import lombok.Data;

/**
 * @author: 28767
 * @date: 2020/9/1 10:00
 */

@Data
public class GithubUser {

    private Long id;
    private String name;
    private String bio;
    private String avatar_url;
}
