package com.bester.platform.platformchain.util;

/**
 * @author liuwen
 * @date 2018/11/28
 */
public class InviteCodeUtil {

    /**
     * Y59ZGRVBALUO1WXMK2N8D3IEFC67JPS4QTH
     */
    private static final char[] SOURCE_ARR = {'Y', 'Z', 'G', 'R', 'V', 'B', 'L', 'U', 'O', 'W', 'X', 'M', 'K', 'N', 'D', 'I', 'E', 'F', 'C', 'J', 'P', 'S', 'Q', 'T', 'H'};

    private static final int INVITE_CODE_LENGTH = 6;

    public static String userInviteCode() {
//        if (userId <= 0) {
//            return "";
//        }
//        StringBuilder sb = new StringBuilder();
//        while (userId > 0) {
//            int mod = userId % SOURCE_ARR.length;
//            userId = (userId - mod) / SOURCE_ARR.length;
//            sb.append(SOURCE_ARR[mod]);
//        }
//        while (sb.length() < INVITE_CODE_LENGTH) {
//            sb.append("A");
//        }
        return "ZXCVBN";
    }
}
