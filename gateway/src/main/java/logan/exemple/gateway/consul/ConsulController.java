package logan.exemple.gateway.consul;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.health.model.Check;
import com.ecwid.consul.v1.health.model.HealthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author logan
 * @Title: ConsulController
 * @ProjectName spring-cloud
 * @Description: TODO
 * @date 2018/10/1213:35
 */
@RestController
@RequestMapping("/consul")
public class ConsulController {

    @RequestMapping("/healthCheck")
    public String home() {
        return "Hello World";
    }
}