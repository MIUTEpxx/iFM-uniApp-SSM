package com.pxx.ifmserver.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;


@Configuration//告诉 Spring 容器这个类是一个配置类，Spring 将会扫描并处理其中定义的 bean 和配置
public class WebConfig implements WebMvcConfigurer {//实现 WebMvcConfigurer 接口 意味着 WebConfig 类将能够覆盖接口中的方法来定制 MVC 行为
    @Override//覆盖 WebMvcConfigurer 接口中的 addResourceHandlers 方法 这个方法允许注册资源处理器
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")//传入一个路径模式。在这个例子中，任何以 /images/ 开始的请求都会被匹配
                .addResourceLocations("classpath:/static/images/")//指定静态资源的位置。这里指定的是类路径（classpath）下的 /static/images/ 目录
                .setCachePeriod(0);// 设置缓存时间为0，表示不缓存 .这是告诉 Spring 不要缓存静态资源，每次请求都会重新加载资源

        // 设置静态资源映射
        // 获取项目根目录的路径
        Path rootPath = Paths.get(System.getProperty("user.dir"));//获取当前工作目录的路径，通常在开发环境中，这是项目的根目录
        Path imagesPath = rootPath.resolve("resources");//获取项目根目录下 resources 文件夹的路径
        // 配置文件系统路径下的资源位置
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + imagesPath.toAbsolutePath().toString() + "/images/");//所有以/images/开头的请求将会从根目录下的resources/images文件夹中查找
        registry.addResourceHandler("/audio/**")
                .addResourceLocations("file:" + imagesPath.toAbsolutePath().toString() + "/audio/");//所有以/audio/开头的请求将会从根目录下的resources/audio中查找
    }

}
