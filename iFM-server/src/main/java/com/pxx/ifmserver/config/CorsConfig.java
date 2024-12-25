package com.pxx.ifmserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

//@Configuration注解表示这是一个配置类，Spring容器会加载这个类中的所有@Bean定义的Bean。
@Configuration
public class CorsConfig {//跨域请求的配置

    // 当前跨域请求的结果可以被缓存的时间。这里默认1天
    private static final long MAX_AGE = 24 * 60 * 60;

    @Bean//@Bean注解表示该方法返回的Bean将被Spring容器管理
    public CorsFilter corsFilter() {
        //创建了UrlBasedCorsConfigurationSource和CorsConfiguration的实例，用于定义跨域规则。
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        // 设置多个允许访问的源地址
        corsConfiguration.setAllowedOrigins(Arrays.asList(
                // 第一个源地址
                "http://localhost:8080",
                // 第二个源地址
                "http://localhost:8081",
                //第三个...
                "http://localhost:5173",
                "http://localhost:8082"
        ));
        // 2 设置访问源请求头:允许所有的请求头
        corsConfiguration.addAllowedHeader("*");
        // 3 设置访问源请求方法:允许所有的HTTP方法（GET, POST, PUT, DELETE等）。
        corsConfiguration.addAllowedMethod("*");
        //设置预检请求的结果可以缓存的最大时间为MAX_AGE。
        corsConfiguration.setMaxAge(MAX_AGE);
        // 4 对接口配置跨域设置:将跨域配置应用到所有路径（/**表示项目中的所有路径）。
        source.registerCorsConfiguration("/**", corsConfiguration);
        //返回一个实例 该实例使用前面设置的跨域配置
        return new CorsFilter(source);
    }
}

