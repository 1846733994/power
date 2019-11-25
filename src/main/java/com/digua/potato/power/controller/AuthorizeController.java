package com.digua.potato.power.controller;

import com.digua.potato.power.dto.AccessTokenDTO;
import com.digua.potato.power.dto.GitHubUserDTO;
import com.digua.potato.power.mapper.UserMapper;
import com.digua.potato.power.model.User;
import com.digua.potato.power.provider.GitHubProvider;
import com.digua.potato.power.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

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

    @Autowired
    private UserService userService;
    /**
     * gitHub第三方登录回调方法
     * @param code   返回的code
     * @param state  返回的state
     * @return
     */
    @GetMapping("/callBack")
    public String callBack(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state")String state,
                           HttpServletResponse response) throws IOException {
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
        if(gitUser!=null){
            //判断用户是否存在于数据库
            User userByAccountId =userService.queryUserByAccountId(gitUser.getId());
            User user = new User();
            if(userByAccountId==null){
                //将用户添加到数据库
                user.setAccountId(gitUser.getId());
                user.setName(gitUser.getName());
                user.setToken(String.valueOf(UUID.randomUUID()));
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                userService.addUser(user);
            }else{
                //如果存在，更新token
                user.setId(userByAccountId.getId());
                user.setToken(UUID.randomUUID().toString());
                user.setGmtModified(System.currentTimeMillis());
                userService.updateUserToken(user);
            }
            Cookie cookie = new Cookie("token",user.getToken());
            //设置cookie的有效时间
            cookie.setMaxAge(60*60);
            response.addCookie(cookie);
            //登录成功
            return "redirect:/";
        }else{
            //登录失败
            return "redirect:/";
        }
    }
}
