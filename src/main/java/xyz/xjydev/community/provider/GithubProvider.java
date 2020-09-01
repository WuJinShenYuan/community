package xyz.xjydev.community.provider;

import com.alibaba.fastjson.JSON;
import okhttp3.*;
import org.springframework.stereotype.Component;
import xyz.xjydev.community.dto.AccessTokenDTO;
import xyz.xjydev.community.dto.GithubUser;

import java.io.IOException;

/**
 * @author: 28767
 * @date: 2020/9/1 9:15
 */

@Component
public class GithubProvider {

    /** 发送code获取accesstoken */
    public String getAccessToken(AccessTokenDTO accessTokenDTO){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string=response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /** 发送accesstoken获取用户信息 */
    public GithubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try{
            Response response = client.newCall(request).execute();
            String string=response.body().string();
            // 把JSON对象转换成java类对象
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        }catch (IOException e){
        }
        return null;
    }
}
