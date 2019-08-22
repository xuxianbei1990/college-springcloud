package college.springcloud.service.model;

import college.springcloud.service.javax.validation.BeanValidateCarNumber;
import lombok.Data;

import java.io.Serializable;

/**
 * User: xuxianbei
 * Date: 2019/8/19
 * Time: 19:52
 * Version:V1.0
 */
@Data
public class CarNumberValidate implements Serializable {
    private static final long serialVersionUID = 955537969976856742L;


    @BeanValidateCarNumber
    private String carNumber;
}
