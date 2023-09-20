package com.shawn.shopproject.util.mailservice;


import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.security.SecureRandom;

@Component
public class RandonCode {

    // 設定亂碼範圍
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    // 設定長度
    private static final int CODE_LENGTH = 6;

    public String RandonCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder(CODE_LENGTH);

        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            code.append(randomChar);
        }
        return code.toString();
    }


    public void saveRandonCode(String email, String randomCode) {

        // 設置兩分鐘內輸入 時間過後便不會存於Redis
        Jedis jedis = new Jedis("localhost", 6379);
        jedis.set(email, randomCode);
        jedis.expire(email, 120);

    }

    public  String getRandonCode(String email) {
        Jedis jedis = new Jedis("localhost", 6379);
        String checkEmail = jedis.get(email);
        return checkEmail;
    }

}
