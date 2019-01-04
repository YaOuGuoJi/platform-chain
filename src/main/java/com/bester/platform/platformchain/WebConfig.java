package com.bester.platform.platformchain;

import com.bester.platform.platformchain.common.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author liuwen
 * @date 2018/11/6
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Resource
    private UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor)
                .excludePathPatterns("/user/isLogin")
                .excludePathPatterns("/index")
                .excludePathPatterns("/user/verification")
                .excludePathPatterns("/user/verificationCode")
                .excludePathPatterns("/user/weChat/verifyCode");
    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600)
                .allowCredentials(true);
    }

}
