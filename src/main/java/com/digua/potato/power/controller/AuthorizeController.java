package com.digua.potato.power.controller;

import com.digua.potato.power.dto.AccessTokenDTO;
import com.digua.potato.power.dto.GitHubUserDTO;
import com.digua.potato.power.provider.GitHubProvider;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
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
        accessTokenDTO.setClient_id("b6f8bbd65a6f8c18cadc");
        accessTokenDTO.setClient_secret("ec27efb9b37e794db29708b5a823ad2addf96ef9");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri("http://localhost/callBack");
        //使用code 访问git  换取 access_token
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        //用返回的access_token 请求git 获取用户信息
        GitHubUserDTO gitUser=gitHubProvider.getGitUserInfo(accessToken);
        System.out.println("=======获取到的用户========"+gitUser.getName());
        return "index";
    }
}
