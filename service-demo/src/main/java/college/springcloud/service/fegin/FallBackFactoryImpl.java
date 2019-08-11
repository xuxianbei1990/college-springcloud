package college.springcloud.service.fegin;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * User: xuxianbei
 * Date: 2019/7/20
 * Time: 15:20
 * Version:V1.0
 */
@Component
public class FallBackFactoryImpl implements FallbackFactory<FallbackFactoryRPC> {

    @Override
    public FallbackFactoryRPC create(Throwable cause) {
        System.err.println(cause.getMessage());
        return ()-> "降级了";
    }
}
