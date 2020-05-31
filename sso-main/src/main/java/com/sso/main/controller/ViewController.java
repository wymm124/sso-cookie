package com.sso.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author wanggy
 * @date 2020/05/30 23:00
 * @description created by IntelliJ IDEA
 */
@Controller
@RequestMapping("/view")
public class ViewController {

    @Autowired
    private RestTemplate restTemplate;

    private final String LOGIN_USER_URL = "http://login.codeshop.com:9001/login/info?token=";

    @GetMapping("/index")
    public String toIndex(@CookieValue(required = false, value = "TOKEN") Cookie cookie, HttpSession session) {
        if (cookie != null) {
            String token = cookie.getValue();
            System.out.println("token" + token);
            if (!StringUtils.isEmpty(token)) {
                Map result = restTemplate.getForObject(LOGIN_USER_URL + token, Map.class);
                session.setAttribute("loginUser", result);
            }
        }
        return "index";
    }
}