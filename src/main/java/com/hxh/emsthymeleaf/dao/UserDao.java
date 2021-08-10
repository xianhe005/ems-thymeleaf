package com.hxh.emsthymeleaf.dao;

import com.hxh.emsthymeleaf.entity.User;

public interface UserDao {

    // 根据用户名查询用户
    User findByUserName(String username);

    // 注册保存
    void save(User user);
}
