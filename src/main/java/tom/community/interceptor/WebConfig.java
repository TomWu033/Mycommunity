package tom.community.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tom.community.model.LoginTicket;

/**
 * 拦截器实现
 */
@Configuration
//@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns需要拦截的地址，excludePathPatterns略过的地址
        registry.addInterceptor(sessionInterceptor).addPathPatterns("/**");//这里所有都拦截
        registry.addInterceptor(loginInterceptor).addPathPatterns("/reglogin");
    }

}
