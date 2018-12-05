package com.bester.platform.platformchain.util;

/**
 * @author liuwen
 * @date 2018/11/28
 */
public class InviteCodeUtil {

    private static final int CODE_LENGTH = 26;
    private static final int INVITE_CODE_LENGTH = 6;

    public static String userInviteCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < INVITE_CODE_LENGTH; i++) {
            char c = (char) (Math.random() * CODE_LENGTH + 'A');
            sb.append(c);
        }
        return sb.toString();
    }
}
