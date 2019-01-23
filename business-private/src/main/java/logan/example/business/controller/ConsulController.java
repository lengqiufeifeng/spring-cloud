package logan.example.business.controller;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.health.model.Check;
import com.ecwid.consul.v1.health.model.HealthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author logan
 * @Title: ${file_name}
 * @Package ${package_name}
 * @Description: ${Description}
 * @date 2018/2/9 14:23
 * @updateBy ${update_by}
 * @updateTime ${update_time}
 */
@RestController
@RequestMapping("/consul" )
public class ConsulController {
    Logger logger = LoggerFactory.getLogger(ConsulController.class);
    @Autowired
    private ConsulClient consulClient;
    @Autowired
    private LoadBalancerClient loadBalancer;

    @Autowired
    private DiscoveryClient discovery;

    @RequestMapping("/getAllServices" )
    public Object all() {
        return discovery.getServices();
    }

    @RequestMapping("/discoveryServices" )
    public Object discovery(String serviceId) {
        return loadBalancer.choose(serviceId);

    }

    @RequestMapping(value = "/deregister/{id}", method = RequestMethod.POST)
    public String unregisterServiceAll(@PathVariable String id) {
        List<HealthService> response = consulClient.getHealthServices(id, false, null).getValue();
        for (HealthService service : response) {
            // 创建一个用来剔除无效实例的ConsulClient，连接到无效实例注册的agent
            ConsulClient clearClient = new ConsulClient(service.getNode().getAddress(), 8500);
            service.getChecks().forEach(check -> {
                if (check.getStatus() != Check.CheckStatus.PASSING) {
                    logger.info("deregister : {}", check.getServiceId());
                    clearClient.agentServiceDeregister(check.getServiceId());
                }
            });
        }
        return null;
    }
}
