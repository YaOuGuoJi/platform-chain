package com.bester.platform.platformchain.common;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.auth0.jwt.interfaces.Claim;
import com.bester.platform.platformchain.util.TokenUtil;
import com.google.common.collect.Maps;
import com.xianbester.api.service.UserAccountService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * @author liuwen
 * @date 2018/11/6
 */
@Component
public class UserInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserInterceptor.class);

    @Reference
    private UserAccountService userAccountService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = TokenUtil.getToken(request);
        if (!StringUtils.isEmpty(token)) {
            try {
                Map<String, Claim> claimMap = TokenUtil.verifyToken(token);
                String userId = claimMap.get("userId").asString();
                request.setAttribute("userId", userId);
                addLoginRecord(userId, response);
                return true;
            } catch (UnsupportedEncodingException e) {
                LOGGER.error("token验证失败！", e);
            }
        }
        sendJsonMessage(response);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handle, Exception ex) {
    }

    private void addLoginRecord(String userId, HttpServletResponse response) throws UnsupportedEncodingException {
        Map<String, String> data = Maps.newHashMap();
        data.put("userId", userId);
        TokenUtil.updateToken2Cookie(response, data);
        userAccountService.addLoginRecord(NumberUtils.toInt(userId));
    }

    private static void sendJsonMessage(HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(JSON.toJSONString(CommonResult.fail(401, "没有登录！")));
        writer.close();
        response.flushBuffer();
    }

}
