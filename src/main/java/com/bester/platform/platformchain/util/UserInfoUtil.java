package com.bester.platform.platformchain.util;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liuwen
 * @date 2018/11/7
 */
public class UserInfoUtil {

    /**
     * 从请求中获取userId
     *
     * @return
     */
    public static int getUserId() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            String userId = (String) request.getAttribute("userId");
            return NumberUtils.toInt(userId);
        }
        return 0;
    }
}
