package com.ccd.backend.mapper;

import com.ccd.backend.entity.Weblinks;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface WeblinksMapper {

//    public int create(Weblinks weblinks);
//    public Weblinks query(int id);
    public int updateWeblinks(Weblinks weblinks);
}