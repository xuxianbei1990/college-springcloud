package college.springcloud.service.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

/**
 * 微服务同一处理
 * @author: xuxianbei
 * Date: 2020/9/22
 * Time: 17:46
 * Version:V1.0
 */
@Component
public class MyRequestInterceptor implements RequestInterceptor {


    /**
     * 可以使用这个方法拿到token
     * ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
     *
     * @param template
     */
    @Override
    public void apply(RequestTemplate template) {
        template.header("AUTHORIZATION", "token");
    }
}
