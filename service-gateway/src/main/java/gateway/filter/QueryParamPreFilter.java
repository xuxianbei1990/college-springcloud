package gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.*;

/**
 * User: xuxianbei
 * Date: 2019/8/11
 * Time: 20:12
 * Version:V1.0
 */
@Component
public class QueryParamPreFilter extends ZuulFilter {


    /**
     * PRE: 该类型的filters在Request routing到源web-service之前执行。用来实现Authentication、选择源服务地址等
     * ROUTING：该类型的filters用于把Request routing到源web-college.springcloud.service，源web-service是实现业务逻辑的服务。这里使用HttpClient请求web-college.springcloud.service。
     * POST：该类型的filters在ROUTING返回Response后执行。用来实现对Response结果进行修改，收集统计数据以及把Response传输会客户端。
     * ERROR：上面三个过程中任何一个出现错误都交由ERROR类型的filters进行处理。
     * 主要关注 pre、post和error。分别代表前置过滤，后置过滤和异常过滤
     * ---------------------
     * @return
     */


    @Override
    public String filterType() {
        System.out.println("filterType");
        return PRE_TYPE;
    }



    @Override
    public int filterOrder() {
        System.out.println("filterOrder");
        //优先级，数字越小，优先级越高
        return PRE_DECORATION_FILTER_ORDER;
    }

    /**
     * true代表生效，false代表不生效。
     * @return
     */
    @Override
    public boolean shouldFilter() {
        System.out.println("shouldFilter");
        RequestContext requestContext = RequestContext.getCurrentContext();
        return !requestContext.containsKey(FORWARD_TO_KEY) && !requestContext.containsKey(SERVICE_ID_KEY);
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        if (request.getParameter("foo") != null) {
            System.out.println("run");
            // put the serviceId in `RequestContext`
            requestContext.put(SERVICE_ID_KEY, request.getParameter("foo"));
        }
        return null;
    }
}
