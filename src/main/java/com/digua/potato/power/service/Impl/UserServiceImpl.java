package com.digua.potato.power.service.Impl;

import com.digua.potato.power.mapper.UserMapper;
import com.digua.potato.power.model.User;
import com.digua.potato.power.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void addUser(User user) {
        userMapper.insert(user);
    }

    @Override
    public void updateUser(User user) {
        userMapper.update(user);
    }

    @Override
    public User queryUserById(Long id) {
        return userMapper.selectById(id);
    }

    @Override
    public User queryUserByToken(String token) {
        return userMapper.findByToken(token);
    }

    @Override
    public void updateUserToken(User user) {
        userMapper.updateUserToken(user);
    }

    @Override
    public User queryUserByAccountId(String accountId) {
        return userMapper.findByAccountId(accountId);
    }
}
