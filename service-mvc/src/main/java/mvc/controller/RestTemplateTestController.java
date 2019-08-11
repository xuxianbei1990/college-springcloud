package mvc.controller;

import com.google.common.collect.Maps;
import college.springcloud.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * User: xuxianbei
 * Date: 2019/8/6
 * Time: 19:47
 * Version:V1.0
 * <p>
 * https://cloud.tencent.com/developer/article/1186676
 */
@RestController
@RequestMapping("/rest/template")
public class RestTemplateTestController {

    @Autowired
    RestTemplate restTemplate;

    @lombok.Data
    static class InnerRes {
        private Status status;
        private Data result;
    }

    @lombok.Data
    static class Status {
        int code;
        String msg;
    }

    @lombok.Data
    static class Data {
        long id;
        String theme;
        String title;
        String dynasty;
        String explain;
        String content;
        String author;
    }


    @GetMapping("/get")
    public String get() {
        // 使用方法一，不带参数
        String url = "https://story.hhui.top/detail?id=666106231640";
        InnerRes res = restTemplate.getForObject(url, InnerRes.class);
//        restTemplate.setInterceptors(Collections.singletonList(new UserAgentInterceptor()));
        StringBuilder sb = new StringBuilder(RequestMethod.GET.name() + "\n" + res.toString());

        //比上面返回的内容更加详细，包括状态 请求头信息。
        ResponseEntity<InnerRes> resEntity = restTemplate.getForEntity(url, InnerRes.class);
        System.out.println(resEntity);

        // 使用方法一，传参替换
        url = "https://story.hhui.top/detail?id={?}";
        res = restTemplate.getForObject(url, InnerRes.class, "666106231640");
        sb.append("\n" + res.toString());
        return sb.toString();
    }

    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = Maps.newHashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key + "", beanMap.get(key));
            }
        }
        return map;
    }

    @GetMapping("/post")
    public String post() {
        String url = "http://localhost:8762/test/post/form";
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("name", "xxb");
        request.add("age", "18");
        String result = restTemplate.postForObject(url, request, String.class);
        System.out.println(result);

        url = "http://localhost:8762/test/post/json";
        Student student = new Student();
        student.setAge(1);
        student.setName("asd");
        Map<String, Object> params = beanToMap(student);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json");
        HttpEntity<Map<String, String>> requestjson = new HttpEntity(params, headers);
        result = restTemplate.postForObject(url, requestjson, String.class);

        System.out.println(result);
        return result;
    }

    //获取post参数
    // 这里使用method模拟不同调用方法
    @RequestMapping("/dispatcher/{method}")
    public Object dispatcher(@PathVariable("method") String method, HttpServletRequest req, HttpServletResponse response) {
        //这一步从数据库读取。
        String keyParams = "name,age";
        // 如果是post请求，需要把参数打包转发
        if (method.equalsIgnoreCase(RequestMethod.POST.name())) {
            String url = "http://localhost:8762/test/post/form";
            MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
            req.getParameterMap().entrySet().stream().forEach(entry -> {
                request.add(entry.getKey(), entry.getValue()[0]);
            });
            return restTemplate.postForObject(url, request, String.class);
        }

        //下面就是组织转发 restTemplate 进行转发
        String inputLine;
        String notityXml = "";
//        try {
//            while ((inputLine = req.getReader().readLine()) != null) {
//                notityXml += inputLine;
//            }
//            req.getReader().close();
//        } catch (IOException ioException) {
//            ioException.printStackTrace();
//        }
        if (method.equalsIgnoreCase("xml")) {
            notityXml = "<xml><appid><![CDATA[wx9644d124588a3aad]]></appid><attach><![CDATA[{\"payType\":\"2\",\"isS2bc\":\"1\"}]]>" +
                    "</attach><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]>" +
                    "</fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1535910151]]></mch_id><nonce_str>" +
                    "<![CDATA[1821068039]]></nonce_str><openid><![CDATA[oi-Mo433pLxhQEpeq563MKPC0LIU]]></openid><out_trade_no>" +
                    "<![CDATA[126992057892864000]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code>" +
                    "<![CDATA[SUCCESS]]></return_code><sign><![CDATA[D21881A253690B8A94D9486E5B73D04D]]></sign><time_end>" +
                    "<![CDATA[20190802182123]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[JSAPI]]></trade_type>" +
                    "<transaction_id><![CDATA[4200000340201908029250143076]]></transaction_id></xml>";
            //xml格式。读取数据库配置xml
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "text/xml");
            String url = "http://localhost:8762/test/post/xml";
            HttpEntity<Map<String, String>> requestjson = new HttpEntity(notityXml, headers);
            return restTemplate.postForObject(url, requestjson, String.class);
        }
        return req.toString();
    }

}
