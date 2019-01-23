package logan.exemple.business.service;

import logan.exemple.business.service.impl.ServiceInfoFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author logan
 * @Title: ServiceInfoClient
 * @ProjectName cloud
 * @Description: TODO
 * @date 2018/9/3013:32
 */
@FeignClient(name = "business-private", fallback = ServiceInfoFallbackFactory.class)
public interface ServiceInfoClient {
    @RequestMapping("/zk/getAllServices" )
    public Object all();


}
