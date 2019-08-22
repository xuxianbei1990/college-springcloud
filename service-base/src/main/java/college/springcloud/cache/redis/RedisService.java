package college.springcloud.cache.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * @author lchm
 * @Description:
 * @ClassName: RedisService
 * @date 2019-04-18 11:09
 */
@Configuration
@EnableCaching
public class RedisService extends CachingConfigurerSupport {

    @Bean
    @ConfigurationProperties(prefix = "redis")
    public RedisConfig getRedisConfig() {
        return new RedisConfig();
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }



//    /**
//     * @return JedisConnectionFactory    返回类型
//     * @throws
//     * @Title: redisConnectionFactory
//     * @Description: 初始化redis工厂
//     * @author bond
//     */
//    @Bean
//    public JedisConnectionFactory redisConnectionFactory(RedisConfig redisConfig) {
//        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
//        jedisConnectionFactory.setHostName(redisConfig.getHost());
//        jedisConnectionFactory.setPort(redisConfig.getPort());
//        jedisConnectionFactory.setPassword(redisConfig.getPassword());
//        jedisConnectionFactory.setTimeout(redisConfig.getTimeout());
//        jedisConnectionFactory.setUsePool(true);
//        jedisConnectionFactory.setDatabase(redisConfig.getDatabase());
//        JedisPoolConfig poolConfig = new JedisPoolConfig();
////        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
//        poolConfig.setMaxIdle(redisConfig.getMaxIdle());
//        poolConfig.setMinIdle(redisConfig.getMinIdle());
//        poolConfig.setMaxWaitMillis(redisConfig.getMaxWaitMillis());
//        poolConfig.setTestOnBorrow(false);
//        jedisConnectionFactory.setPoolConfig(poolConfig);
//        return jedisConnectionFactory;
//    }
//
//    /**
//     * @param @param  cf
//     * @param @param  redisSerializer
//     * @param @return 参数说明
//     * @return RedisTemplate<String,String>    返回类型
//     * @throws
//     * @Title: 注入reditsTemplate
//     * @Description: 设置连接及KEY、VALUE的String序列化方式
//     * @author bond
//     */
//    @Bean
//    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf, RedisSerializer<?> redisSerializer) {
//        RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
//        redisTemplate.setConnectionFactory(cf);
//        redisTemplate.setDefaultSerializer(redisSerializer);
//        return redisTemplate;
//    }
//
//    /**
//     * RedisTemplate默认采用的是JDK的序列化策略，保存的key和value都是采用此策略序列化保存
//     * 所有Template的key都采用String的序列化方式，而value的序列化方式可以采用不同的序列化方式
//     */
//    @Bean
//    @Override
//    public CacheManager cacheManager() {
//        return new RedisCacheManager(redisTemplate(redisConnectionFactory(getRedisConfig()), new StringRedisSerializer()));
//    }
}
