package logan.exemple.business;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


/**
 * @author logan
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${Description}
 * @date 2018/2/5 18:04
 * @updateBy ${update_by}
 * @updateTime ${update_time}
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients
public class BusinessApp {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() { // equals to RestTemplate
        // restTemplate=new RestTemplate();
        return new RestTemplate();
    }


    public static void main(String[] args) {
        SpringApplication.run(BusinessApp.class, args);
        System.err.println("--------------------" );
//        SpringApplicationBuilder springApplicationBuilder=   new SpringApplicationBuilder(ZookeeperApp.class);
//        springApplicationBuilder.web(WebApplicationType.SERVLET).run(args);
//        System.err.println("--------------------");

    }
}
