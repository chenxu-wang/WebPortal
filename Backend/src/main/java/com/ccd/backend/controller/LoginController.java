package com.ccd.backend.controller;


import com.ccd.backend.entity.User;
import com.ccd.backend.framework.jwt.JWTUtil;
import com.ccd.backend.service.UserService;
import com.ccd.backend.utils.Maps;
import com.ccd.backend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {
    @Autowired
    UserService userService;
    @PostMapping("/login")
    public Result login(@RequestBody Map<String,String> map){
        String username = map.get("username");
        String password = map.get("password");
        User user=userService.login(username,password);
//        User user=userService.insecureLogin(username,password);
        if(user!=null){
            String token= JWTUtil.sign(user);
            return Result.ok(Maps.build().put("token",token).put("user",user).getMap());
        }else{
            return Result.fail("Username or password is wrong");
        }
    }
}
