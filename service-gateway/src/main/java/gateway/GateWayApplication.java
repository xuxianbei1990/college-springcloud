package gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * User: xuxianbei
 * Date: 2019/7/19
 * Time: 11:53
 * Version:V1.0
 * 为什么要用网关，解决了什么问题？可以让客户只需要知道网关地址。
 * 单点问题？压力问题？所有请求压倒这个网关，转发？
 *
 * https://cloud.spring.io/spring-cloud-static/Edgware.SR3/single/spring-cloud.html
 */
@SpringBootApplication
@EnableZuulProxy
public class GateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class, args);
    }
}
