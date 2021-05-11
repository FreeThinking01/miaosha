package com.mooc.miaosha.validation;

    /**
    * @Description: 自定义注解判断Mobile格式，Target指明注解作用对象，
     * Retention指明存活周期，因为要在程序运行期间参数校验，因此选择Runtime
     * @Constraint内要求传入一个class参数，这个class是处理逻辑
    * @Param: 注解内定义注解参数，可用default设定一个默认值
    * @return:
    * @Author: lishuaiyun
    * @Date: 2021/5/7
    */
@java.lang.annotation.Target({java.lang.annotation.ElementType.FIELD})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Documented
@javax.validation.Constraint(validatedBy = {IsMobileValidator.class})
public @interface IsMobile {

    boolean required() default true;

    java.lang.String message() default "手机号码格式有误";

    java.lang.Class<?>[] groups() default {};

    java.lang.Class<? extends javax.validation.Payload>[] payload() default {};
}
