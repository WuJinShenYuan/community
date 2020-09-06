package xyz.xjydev.community.dto;

import lombok.Data;

/**
 * @author: 28767
 * @date: 2020/9/1 9:18
 */

@Data
public class AccessTokenDTO {

    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_url;
    private String state;
}
