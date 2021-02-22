package com.lin.config;

import com.lin.interceptor.*;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {

            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/", "classpath:/templates/"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/css/**","/js/**","/img/**","/login", "/login.html","/logout", "/student/**", "/teacher/**", "/login?*", "/center/**", "/admin/**", "/404.html", "/login_again.html");
        registry.addInterceptor(new StudentInterceptor()).addPathPatterns("/student/**").excludePathPatterns();
        registry.addInterceptor(new TeacherInterceptor()).addPathPatterns("/teacher/**").excludePathPatterns();
        registry.addInterceptor(new CenterInterceptor()).addPathPatterns("/center/**").excludePathPatterns();
        registry.addInterceptor(new AdminInterceptor()).addPathPatterns("/admin/**").excludePathPatterns();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!registry.hasMappingForPattern("/webjars/**")) {

            registry.addResourceHandler("/webjars/**").addResourceLocations(
                    "classpath:/META-INF/resources/webjars/");
        }
        if (!registry.hasMappingForPattern("/**")) {

            registry.addResourceHandler("/**").addResourceLocations(
                    CLASSPATH_RESOURCE_LOCATIONS);
        }
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    }
}


