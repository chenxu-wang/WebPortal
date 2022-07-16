package com.ccd.backend.controller;

import com.ccd.backend.entity.User;
import com.ccd.backend.entity.Weblinks;
import com.ccd.backend.mapper.WeblinksMapper;
import com.ccd.backend.service.UserService;
import com.ccd.backend.service.WeblinksService;
import com.ccd.backend.utils.Maps;
import com.ccd.backend.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/weblinks")
public class WeblinksController {
    @Autowired
    private WeblinksService weblinksService;

    @PostMapping("/create")
    public Result create(@RequestBody Weblinks weblinks) {
        weblinksService.create(weblinks);
        return Result.ok(weblinks);
    }
    @PostMapping("/delete")
    public Result delete(@RequestBody Map<String, String> map) {
        Integer id = Integer.parseInt(map.get("id"));
        weblinksService.delete(id);
        return Result.ok(id);
    }
    @PostMapping("/update")
    public Result update(@RequestBody Map<String, String> map) {
        Integer id = Integer.parseInt(map.get("id"));
        String title = map.get("title");
        String link = map.get("link");
        String description = map.get("description");
        String category = map.get("category");
        Weblinks weblinks = new Weblinks(id, link, title,category,description);
        return Result.ok( weblinksService.update(weblinks));
    }
    @PostMapping("/query")
    public Result query() {
        return Result.ok(Maps.build().put("weblinks",weblinksService.query()).getMap());
    }
    @PostMapping("/queryByCat")
    public Result queryByCat(@RequestBody Map<String, String> map) {
        String category = map.get("category");
        return Result.ok(Maps.build().put("weblinks",weblinksService.queryByCat(category)).getMap());
    }
}
