package logan.exemple.business.service.impl;

import feign.hystrix.FallbackFactory;
import logan.exemple.business.service.ServiceInfoClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author 韩韩
 * @Title: ServiceInfoImpl
 * @ProjectName cloud
 * @Description: TODO
 * @date 2018/9/3013:59
 */
@Component
public class ServiceInfoFallbackFactory implements FallbackFactory<ServiceInfoClient> {
    private static final Logger logger = LoggerFactory.getLogger(ServiceInfoFallbackFactory.class);

    @Override
    public ServiceInfoClient create(Throwable cause) {
        logger.error("ServiceInfoClient error:",cause);
    return new ServiceInfoClient() {
        @Override
        public Object all() {
            return null;
        }
    };
    }
}
