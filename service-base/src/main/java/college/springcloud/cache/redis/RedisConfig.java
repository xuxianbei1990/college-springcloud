package college.springcloud.cache.redis;

import lombok.Data;

/**
 * @author lchm
 * @Description: redis 配置参数
 * @ClassName: RedisConfig
 * @date 2019-04-18 10:47
 */
@Data
public class RedisConfig {

    /**指定使用哪个redis库 0-15*/
    private int database;

    /**redis所在服务器ip*/
    private String host;

    /**端口：6379*/
    private int port;

    /**请求redis超时时间:单位ms*/
    private int timeout;

    /**链接redis密码*/
    private String password;

    /**最大空闲数：空闲链接数大于maxIdle时，将进行回收*/
    private int maxIdle;

    /**最小空闲数：低于minIdle时，将创建新的链接*/
    private int minIdle;

    /**最大等待时间：单位ms*/
    private int maxWaitMillis;

}
