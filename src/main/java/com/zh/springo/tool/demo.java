package com.zh.springo.tool;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @description:
 * @author: zbxComputer
 * @time: 2023/3/24 0:17
 */
public class demo {
    public static void main(String[] args) {
        String encode1 = new BCryptPasswordEncoder().encode("465897897987998");
        byte[] encode = Base64.getEncoder().encode("465897897987998".getBytes(StandardCharsets.UTF_8));
        System.out.println(Base64.getEncoder().encodeToString(encode));
    }
}
