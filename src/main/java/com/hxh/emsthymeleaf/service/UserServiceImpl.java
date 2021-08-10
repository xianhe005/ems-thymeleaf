package com.hxh.emsthymeleaf.service;
import com.hxh.emsthymeleaf.dao.UserDao;
import com.hxh.emsthymeleaf.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void register(User user) {
        // 判断是否存在
        User userDb = userDao.findByUserName(user.getUsername());
        if (!ObjectUtils.isEmpty(userDb)) {
            throw new RuntimeException("用户名已存在");
        }
        // 不存在，注册
        // 密码加密
        String newP = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(newP);
        userDao.save(user);
    }

    @Override
    public User login(String username, String password) {
        User userDb = userDao.findByUserName(username);
        if (ObjectUtils.isEmpty(userDb)) {
            throw new RuntimeException("用户名输入错误");
        }
        if (!userDb.getPassword().equals(DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))) {
            throw new RuntimeException("用户密码输入错误");
        }
        return userDb;
    }
}
