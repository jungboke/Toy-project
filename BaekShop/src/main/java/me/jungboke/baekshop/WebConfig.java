package me.jungboke.baekshop;

import me.jungboke.baekshop.web.intercepter.LogIntercepter;
import me.jungboke.baekshop.web.intercepter.LoginCheckIntercepter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogIntercepter())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "error");

        registry.addInterceptor(new LoginCheckIntercepter())
                .order(2)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error", "/error*", "/api/**", "/", "/login", "/logout", "/members/new");
    }
}
