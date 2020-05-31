package com.sso.login.controller;

import com.sso.login.pojo.User;
import com.sso.login.util.LoginCacheUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;

/**
 * @author wanggy
 * @date 2020/05/30 23:12
 * @description created by IntelliJ IDEA
 */
@Controller
@RequestMapping("/view")
public class ViewController {

    @GetMapping("/login")
    public String toLogin(@RequestParam(required = false, defaultValue = "") String target, HttpSession session,
                          @CookieValue(required = false, value = "TOKEN") Cookie cookie) {

        if (StringUtils.isEmpty(target)) {
            target = "www.codeshop.com:9000";
        }

        // 获取 cookie 信息，如果一登录，直接充定向到 target 页面
        if (cookie != null) {
            String token = cookie.getValue();
            User user = LoginCacheUtil.loginCacheMap.get(token);
            if (user != null) {
                return "redirect:" + target;
            }
        }

        // todo 校验地址的合法性
        // 充定向地址
        session.setAttribute("target", target);
        return "login";
    }
}
