package com.bester.platform.platformchain.util;

import java.util.Random;

/**
 * @author liuwen
 * @date 2018/12/13
 */
public class RandomUtil {

    public static String getStringRandom(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            if( "char".equalsIgnoreCase(charOrNum) ) {
                sb.append((char) (random.nextInt(26) + 65));
            } else {
                sb.append(String.valueOf(random.nextInt(10)));
            }
        }
        return sb.toString();
    }

    public static String justStringRandom(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            sb.append((char) (random.nextInt(26) + 65));
        }
        return sb.toString();
    }

    public static String justNumberRandom(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
