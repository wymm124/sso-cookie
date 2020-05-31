package com.sso.login.controller;

import com.sso.login.pojo.User;
import com.sso.login.util.LoginCacheUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author wanggy
 * @date 2020/05/31 14:45
 * @description created by IntelliJ IDEA
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static Set<User> loginUserSet;

    static {
        loginUserSet = new HashSet<>();
        loginUserSet.add(new User(1, "xs", "123"));
        loginUserSet.add(new User(2, "lwj", "123"));
        loginUserSet.add(new User(3, "wx", "123"));
    }

    @PostMapping
    public String doLogin(User user, HttpSession session, HttpServletResponse response) {
        String target = session.getAttribute("target").toString();
            Optional<User> optionalUser = loginUserSet.stream().filter(loginUser ->
                    loginUser.getUsername().equals(user.getUsername()) && loginUser.getPassword().equals(user.getPassword()))
                    .findFirst();
            if (optionalUser.isPresent()) {
                // 保存用户信息
                String token = UUID.randomUUID().toString();
                LoginCacheUtil.loginCacheMap.put(token, user);
                // 保存 cookie 信息
                Cookie cookie = new Cookie("TOKEN", token);
                cookie.setDomain("codeshop.com");
                response.addCookie(cookie);
            } else {
                // 用户名或密码错误
                session.setAttribute("msg", "用户名或密码错误");
                return "login";
            }
        // 充定向到目标地址
        return "redirect:" + target;
    }

    @GetMapping("/info")
    @ResponseBody
    public ResponseEntity<User> getInfo(String token) {
        if (!StringUtils.isEmpty(token)) {
            User user = LoginCacheUtil.loginCacheMap.get(token);
            if (user != null) {
                return ResponseEntity.ok(user);
            }
        }
        return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
    }
}
