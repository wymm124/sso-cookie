package com.sso.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author wanggy
 * @date 2020/05/31 22:17
 * @description created by IntelliJ IDEA
 */
@SpringBootApplication
public class CartApp {

    public static void main(String[] args) {
        SpringApplication.run(CartApp.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
