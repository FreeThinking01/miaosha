package com.mooc.miaosha.validation;

import com.mooc.miaosha.util.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
* @Description: 处理逻辑需要实现ConstraintValidator接口，接口有两个泛型，
 * 第一个是自定义的注解类，第二个就是要验证的数据的类型(例如写了String类型
 * 的数据，那么这个注解就要放在String类型的字段上才会起作用, 最简便的写成Object，
 * 那么它可以接收任何数据类型的数据)。里面有两个方法，initialize和isValid，
 * 第一个是初始化方法，第二个是验证的逻辑方法，返回true,则验证通过，否则则不通过。
* @Param:
* @return:
* @Author: lishuaiyun
* @Date: 2021/5/7
*/
public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    private boolean required = false;

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (required){
            return ValidatorUtil.isMobile(value);
        }else {
            if (StringUtils.isEmpty(value)){
                return true;
            }else {
                return ValidatorUtil.isMobile(value);
            }
        }
    }
}
