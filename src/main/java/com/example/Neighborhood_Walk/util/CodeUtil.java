package com.example.Neighborhood_Walk.util;

import java.util.Random;

public class CodeUtil {

    public static String generateCode(int length) {
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            code.append(random.nextInt(10)); // 生成0-9的随机数
        }
        return code.toString();
    }
}
