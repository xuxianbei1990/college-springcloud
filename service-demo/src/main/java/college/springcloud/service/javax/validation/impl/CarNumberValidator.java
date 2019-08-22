package college.springcloud.service.javax.validation.impl;

import college.springcloud.service.javax.validation.BeanValidateCarNumber;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: xuxianbei
 * Date: 2019/8/19
 * Time: 19:48
 * Version:V1.0
 */
public class CarNumberValidator implements ConstraintValidator<BeanValidateCarNumber, String> {
    @Override
    public void initialize(BeanValidateCarNumber constraintAnnotation) {

    }

    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    /**
     * xxb-123456
     *
     * @param value
     * @param context
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        String[] values = value.split("-");
        boolean prefix = values[0].startsWith("xxb");
        boolean suffix = isNumeric(values[1]);
        return prefix && suffix;
    }
}
