package logan.exemple.gateway.base;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author logan
 * @Title: ExceptionContorller
 * @ProjectName spring-cloud
 * @Description: TODO
 * @date 2018/10/1514:02
 */
@Controller
public class ExceptionContorller {
    @GetMapping("/fallback")
    public String fallback(ServerHttpRequest shrq) {
        return "Hello World!\nfrom gateway";
    }
}
