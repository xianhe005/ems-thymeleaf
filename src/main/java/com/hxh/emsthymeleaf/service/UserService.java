package com.hxh.emsthymeleaf.service;

import com.hxh.emsthymeleaf.entity.User;

public interface UserService {

    /**
     * 用户注册
     * @param user user
     */
    void register(User user);

    /**
     * 登录
     * @param username username
     * @param password password
     */
    User login(String username, String password);
}
