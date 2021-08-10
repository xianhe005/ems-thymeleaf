package com.hxh.emsthymeleaf.controller;

import com.hxh.emsthymeleaf.entity.User;
import com.hxh.emsthymeleaf.service.UserService;
import com.hxh.emsthymeleaf.utils.VerifyCodeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequestMapping("user")
public class UserController {


    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 生成验证码
     */
    @RequestMapping("generateImageCode")
    public void generateImageCode(HttpSession session, HttpServletResponse response) throws IOException {
        String code = VerifyCodeUtils.generateVerifyCode(4);
        // code 放入session
        session.setAttribute("code", code);
        System.out.println(code);

        // 写出图片
        //FileOutputStream os = new FileOutputStream("C:\\Users\\hxsz\\IdeaProjects\\ems-jsp\\aa.png");
        response.setContentType("image/png"); // 响应的图片类型
        VerifyCodeUtils.outputImage(220, 80, response.getOutputStream(), code);
    }


    /**
     * 用户注册
     *
     * @param user user
     * @param code code
     */
    @RequestMapping("register")
    public String register(User user, String code, HttpSession session) throws UnsupportedEncodingException {
        log.debug("code:{}", code);
        log.debug("user.username:{},user.getRealname:{},user.getPassword:{},user.getGender:{}",
                user.getUsername(), user.getRealname(), user.getPassword(), user.getGender());
        try {
            //比较code
            String sessionCode = (String) session.getAttribute("code");
            if (!code.equalsIgnoreCase(sessionCode)) {
                throw new RuntimeException("验证码输入错误");
            }
            userService.register(user);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return "redirect:/register?msg=" + URLEncoder.encode(e.getMessage(), "UTF-8");
        }

        return "redirect:/login";
    }

    /**
     * 登录
     */
    @RequestMapping("login")
    public String login(String username, String password, HttpSession session) throws UnsupportedEncodingException {
        log.debug("username:{}, password:{}", username, password);
        try {
            User user = userService.login(username, password);
            // 保存用户登录成功标记
            session.setAttribute("user", user);
            return "redirect:/employee/list";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login?msg=" + URLEncoder.encode(e.getMessage(), "UTF-8");
        }
    }

    /**
     * 登出
     */
    @RequestMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate(); // session失效
        return "redirect:/login";
    }
}
