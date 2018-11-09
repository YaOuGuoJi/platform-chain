package com.bester.platform.platformchain;

import com.bester.platform.platformchain.common.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(userInterceptor)
                        .excludePathPatterns("/user/isLogin")
                        .excludePathPatterns("/user/login")
                        .excludePathPatterns("/index")
                        .excludePathPatterns("/user/register");
            }
        };
    }
}
