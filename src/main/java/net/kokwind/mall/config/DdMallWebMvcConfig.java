package net.kokwind.mall.config;

import net.kokwind.mall.common.Constant;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 描述：     配置地址映射
 */
@Configuration
@EnableWebMvc
public class DdMallWebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/admin/**").addResourceLocations("classpath:/static/admin/");
        registry.addResourceHandler("/images/**").addResourceLocations("file:" + Constant.FILE_UPLOAD_DIR);
        // 解决 SWAGGER2 404报错
        registry.addResourceHandler("swagger-ui.html").addResourceLocations(
                "classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations(
                "classpath:/META-INF/resources/webjars/");
    }
}