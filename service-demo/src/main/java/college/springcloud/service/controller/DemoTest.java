package college.springcloud.service.controller;

import college.springcloud.cache.redis.RedisService;
import college.springcloud.model.Student;
import college.springcloud.service.fegin.LoadBalanceClient;
import college.springcloud.service.fegin.StoreClient;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;

/**
 * User: EDZ
 * Date: 2019/7/16
 * Time: 9:19
 * Version:V1.0
 */
@RestController
public class DemoTest {


    //测试ribbon
    @Autowired
    LoadBalanceClient loadBalanceClient;

    //测试远程调用
    @Resource
    StoreClient storeClient;

    @Autowired
    RedisService redisService;

    @GetMapping("/demo/test")
    public String testFegin() {
        return "testFegin";
    }

    @FunctionalInterface
    interface Test {
        void sayHay(String message);
    }

    public static void main(String[] args) {
        Test test = (message) -> {
            System.out.println("2" + message);
        };
        test.sayHay("ss");
    }

    @GetMapping("/redis/get")
    public String redisGet() {
        return redisService.getRedisConfig().getHost();
    }

    //测试所有请求
    //结论下面的写法可以接收所有请求。
    @RequestMapping("/test/quest")
    public String testQuest() {
        return "success";
    }


    @PostMapping("/test/post/form")
    public String testPost(Student student) {
        return student.toString();
    }

    @PostMapping("/test/post/json")
    public String testPostJson(@RequestBody Student student) {
        return student.toString();
    }


    @PostMapping("/test/post/xml")
    public HttpServletResponse testPostXml(HttpServletRequest request, HttpServletResponse response) {

        String inputLine;
        String notityXml = "";
        try {
            while ((inputLine = request.getReader().readLine()) != null) {
                notityXml += inputLine;
            }
            request.getReader().close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        System.out.println(notityXml);
        try {
            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            String resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                    + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
            out.write(resXml.getBytes());
            out.flush();
            out.close();
        } catch (IOException io) {
            io.printStackTrace();
        }
        return response;
    }

    //测试远程调用
    @GetMapping("/demo/fegin")
    public String demoFegin() {
        //String类型
        String result = storeClient.getString("sky");
        Student student = new Student();
        student.setName("dsf");
        student.setAge(112);
        //类
//        student = storeClient.getStudent(student);
//        result = result + "\n student: \n" + JSONObject.toJSONString(student);
//        //列表
//        result = result + "\n student: \n" + JSONObject.toJSONString(storeClient.getStudents());
        result = result + "\n studentNOQueryMap: \n" +storeClient.getStudentNOQueryMap(student);
        result = result + "\n studentQueryMap: \n" + storeClient.getStudentQueryMap(student);


        return result;
    }

    @PostMapping("/demo/student/api")
    public Integer stduentApi(@RequestBody Student student) {
        return storeClient.getStudentInt(student);
    }

    //测试负载均衡
    @GetMapping("/demo/loadbalance")
    public String demoLoadbalance(Integer times) {
        for (int i = 0; i < times; i++) {
            loadBalanceClient.testLoadBalance();
        }
        return "succcess";
    }


}
