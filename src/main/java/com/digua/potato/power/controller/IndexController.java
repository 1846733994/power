package com.digua.potato.power.controller;

import com.digua.potato.power.model.User;
import com.digua.potato.power.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String index (HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies==null|| cookies.length==0){
            return "index";
        }
        for (int i = 0; i <cookies.length ; i++) {
            if(cookies[i].getName().equals("token")){
                String token = cookies[i].getValue();
                User user = userService.queryUserByToken(token);
                request.getSession().setAttribute("user",user);
                break;
            }
        }
        return "index";
    }

}
