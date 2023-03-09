package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.persistence.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UtilService {
    private UserMapper userMapper;
    public UtilService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    public Integer getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userMapper.getUser(userName);
        Integer userId = user.getUserId();
        return userId;
    }
}
