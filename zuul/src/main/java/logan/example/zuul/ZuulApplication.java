package logan.example.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


/**
 * 继承SpringBootServletInitializer，打包war
 * * Created by lzr on 2017/4/15.
 */
@SpringBootApplication
@EnableZuulProxy
//@EnableEurekaClient
public class ZuulApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        SimpleHostRoutingFilter
        return application.sources(ZuulApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ZuulApplication.class, args);
    }
}
