package com.example.cybersociety.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
public class CookieController {

    @RequestMapping("/setcookie")
    public String setCookie() {
        Cookie cookie = new Cookie("cookie_nomi", "cookie_qiymati");
        cookie.setMaxAge(24 * 60 * 60); // 1 kun
        cookie.setPath("/");
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        response.addCookie(cookie);

        return "Cookie muvaffaqiyatli qo'shildi.";
    }
}