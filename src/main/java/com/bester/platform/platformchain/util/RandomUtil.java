package com.bester.platform.platformchain.util;

import java.util.Random;

/**
 * @author liuwen
 * @date 2018/12/13
 */
public class RandomUtil {

    /**
     * 生成字符+数字的随机字符串
     *
     * @param length
     * @return
     */
    public static String charAndNumberRandom(int length) {
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

    /**
     * 随机字母字符串
     *
     * @param length
     * @return
     */
    public static String charsRandom(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            sb.append((char) (random.nextInt(26) + 65));
        }
        return sb.toString();
    }

    /**
     * 随机数字字符串
     *
     * @param length
     * @return
     */
    public static String justNumberRandom(int length) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for(int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }
}
