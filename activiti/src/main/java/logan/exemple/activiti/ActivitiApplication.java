package logan.exemple.activiti;

import org.activiti.spring.boot.SecurityAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 继承SpringBootServletInitializer，打包war
 * * Created by lzr on 2017/4/15.
 * exclude org.activiti.spring.boot.SecurityAutoConfiguration 不然启动失败
 * java.lang.ArrayStoreException: sun.reflect.annotation.TypeNotPresentExceptionProxy
 */
//@EnableDiscoveryClient
@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
public class ActivitiApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(ActivitiApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ActivitiApplication.class, args);
    }
}
