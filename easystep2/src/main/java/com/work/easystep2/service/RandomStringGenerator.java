package com.work.easystep2.service;

import java.security.SecureRandom;

import org.springframework.stereotype.Service;

@Service
public class RandomStringGenerator {
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    // 随机数生成器
    private static final SecureRandom random = new SecureRandom();

    // 生成指定长度的随机字符串
    public String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

}
