package com.bester.platform.platformchain.enums;

/**
 * @author zhangqiang
 * @date 2018-12-6
 */

public enum UserVipLevel {

    NON_VIP(0, "非会员"),

    BlackGold(10, "黑金会籍");


    UserVipLevel(int level, String description) {
        this.level = level;
        this.description = description;
    }

    public int level;

    public String description;

}
