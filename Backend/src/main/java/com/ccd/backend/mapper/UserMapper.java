package com.ccd.backend.mapper;

import com.ccd.backend.entity.User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserMapper {
    public User login(Map<String,Object> paramMap);
    public User detail(Map<String,Object> paramMap);
    public int create(User user);
    public User query(int id);
}
