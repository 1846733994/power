package com.digua.potato.power.provider;

import com.alibaba.fastjson.JSON;
import com.digua.potato.power.dto.AccessTokenDTO;
import com.digua.potato.power.dto.GitHubUserDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * provider 供应者  提供者
 * git请求提供者
 */
@Component
public class GitHubProvider {
    /**
     * 使用OKHttp访问git 将git回调的code 再次用post请求
     * 访问git 换取 access——token
     *
     * @param accessTokenDTO
     * @return
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        //使用Okhttp 来进行http请求
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body).build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String access_token=string.split("&")[0].split("=")[1];
            System.out.println("========access_token========="+access_token);
            return access_token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过换取的token  访问github 获取用户信息
     *
     * @param accessToken
     * @return
     */
    public GitHubUserDTO getGitUserInfo(String accessToken) {
        //使用OKHttp请求
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token="+accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String userInfo = response.body().string();
            System.out.println("=========userInfo=============="+userInfo.toString());
            GitHubUserDTO userDTO = JSON.parseObject(userInfo, GitHubUserDTO.class);
            return userDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
