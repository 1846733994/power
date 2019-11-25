package com.digua.potato.power.service;

import com.digua.potato.power.model.User;


public interface UserService {

    void addUser(User user);

    void updateUser(User user);

    User queryUserById(Long id);

    User queryUserByToken(String token);

    void updateUserToken(User user);

    User queryUserByAccountId(String id);
}
