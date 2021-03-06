package college.springcloud.service.fegin;

import college.springcloud.model.Student;
import feign.QueryMap;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * User: xuxianbei
 * Date: 2019/7/16
 * Time: 9:36
 * Version:V1.0
 */
//突然我想让FeignClient 支持Rest 后面试验下
//支持所有的请求方式 就是这个 "org.springframework.cloud:spring-cloud-starter-openfeign"
@FeignClient("college.springcloud.service-web-fegin")
@RequestMapping("/fegin")
public interface StoreClient {

//    @RequestMapping(value = "/getStudents", method = RequestMethod.GET)
//    List<Student> getStudents();

//    //为什么这里需要加参数说明，不能自动转换？
//    //答：这个要从反射的原理说明。巴拉巴拉
    @RequestMapping(value = "/getString", method = RequestMethod.GET)
    String getString(@RequestParam("key") String key);

    //这个感觉不是特别友好。
    //我的解决思路是在原有的基础在封装一层；把所有参数序列化为字符串再进行传输
    @RequestMapping(value = "/getStudent", method = RequestMethod.POST)
    Student getStudent(@RequestBody Student student);

    @PostMapping(value = "/getStudent/Integer")
    Integer getStudentInt(@RequestBody Student student);

    //低版本如果多参数强制把get转换成post？
    @GetMapping("/getString/queryMap/no")
    Student getStudentNOQueryMap(Student key);

    @GetMapping("/getString/queryMap")
    Student getStudentQueryMap(@QueryMap Student key);

}
