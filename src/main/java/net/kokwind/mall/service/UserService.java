package net.kokwind.mall.service;

import net.kokwind.mall.model.dao.UserMapper;
import net.kokwind.mall.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User getUser(){
        return userMapper.selectByPrimaryKey(1);
    }
}
