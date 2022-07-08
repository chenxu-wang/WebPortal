package com.ccd.backend.controller;

import com.ccd.backend.entity.User;
import com.ccd.backend.framework.exception.MyException;
import com.ccd.backend.service.UserService;
import com.ccd.backend.utils.Maps;
import com.ccd.backend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
//    @Autowired
//    private ;

    @PostMapping("/create")
    public Result create(@RequestBody User user) {
        userService.create(user);
        return Result.ok(user);
    }

    @PostMapping("/query")
    public Result query(@RequestBody Map<String, String> map) {
        Integer id = Integer.parseInt(map.get("id"));
        User user = userService.query(id);
        return Result.ok(Maps.build().put("user",user).getMap());
    }
}

