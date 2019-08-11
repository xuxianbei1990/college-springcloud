package college.springcloud.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * User: xuxianbei
 * Date: 2019/8/11
 * Time: 21:11
 * Version:V1.0
 */
@ControllerAdvice
public class GlobalException {

    @ResponseBody
    @ExceptionHandler(BindException.class)
    public ResponseEntity<?> exceptionHandler(HttpServletRequest request, Exception exception) {
        Map<String, Object> errorResult = new HashMap<>(16);
        errorResult.put("retEntity", exception.getMessage());
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.status(500);
        return bodyBuilder.body(errorResult);
    }
}
