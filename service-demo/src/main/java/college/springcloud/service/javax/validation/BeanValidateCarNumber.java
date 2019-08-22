package college.springcloud.service.javax.validation;

import college.springcloud.service.javax.validation.impl.CarNumberValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * User: xuxianbei
 * Date: 2019/8/19
 * Time: 19:37
 * Version:V1.0
 */

@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {CarNumberValidator.class})
public @interface BeanValidateCarNumber {

    String message() default "{service.demo.carnumber.invalidate.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
