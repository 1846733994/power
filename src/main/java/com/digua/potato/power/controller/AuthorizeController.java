package com.digua.potato.power.controller;

import com.digua.potato.power.dto.AccessTokenDTO;
import com.digua.potato.power.dto.GitHubUserDTO;
import com.digua.potato.power.provider.GitHubProvider;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

/**
 * 授权控制器
 */
@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;
    @Value("${github.client.id}")
    private String  clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUrl;
    /**
     * gitHub第三方登录回调方法
     * @param code   返回的code
     * @param state  返回的state
     * @return
     */
    @GetMapping("/callBack")
    public String callBack(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state) throws IOException {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUrl);
        //使用code 访问git  换取 access_token
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        //用返回的access_token 请求git 获取用户信息
        GitHubUserDTO gitUser=gitHubProvider.getGitUserInfo(accessToken);
        System.out.println("=======获取到的用户========"+gitUser.getName());
        return "index";
    }
}
