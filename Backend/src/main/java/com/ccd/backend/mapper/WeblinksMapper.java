package com.ccd.backend.mapper;

import com.ccd.backend.entity.Weblinks;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface WeblinksMapper {

    public int create(Weblinks weblinks);
    public int updateWeblinks(Weblinks weblinks);
    public List<Weblinks> query();
    public List<Weblinks> queryByCat(String category);
    public int delete(Integer id);
    public int update(Weblinks weblinks);
}