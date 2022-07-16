package com.ccd.backend.service;

import com.ccd.backend.entity.User;
import com.ccd.backend.entity.Weblinks;
import com.ccd.backend.mapper.UserMapper;
import com.ccd.backend.mapper.WeblinksMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeblinksService {
    @Autowired
    private WeblinksMapper weblinksMapper;
    public int create(Weblinks weblinks){
        return weblinksMapper.create(weblinks);
    }
    public List<Weblinks> query(){
        return weblinksMapper.query();
    }
    public List<Weblinks> queryByCat(String category){
        return weblinksMapper.queryByCat(category);
    }
    public int delete(Integer id){ return weblinksMapper.delete(id); }
    public int update(Weblinks weblinks){ return weblinksMapper.update(weblinks); }
}
