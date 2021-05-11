package com.mooc.miaosha.config;

import com.mooc.miaosha.access.UserContext;
import com.mooc.miaosha.domain.MiaoshaUser;
import com.mooc.miaosha.service.MiaoshaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @Description: 参数解析器会对controller层方法的参数执行解析器中的方法，如果参数是支持的解析类型，会执行相应方法。
 *              编写UserArgumentResolver,这会根据当前请求中的cookie与redis中的token对比查询一个user对象并赋
 *              值给传入的参数MiaoshaUser中
* @Param:
* @return: 
* @Author: lishuaiyun
* @Date: 2021/5/10
*/
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    MiaoshaUserService userService;

    /**
     * 给定的方法参数是否由此解析器支持,如果此解析器支持所提供的参数，则为true, 否则为false
     * */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        return clazz == MiaoshaUser.class;
    }

    /**
    * @Description: 将方法参数解析为给定请求的参数值。 ModelAndViewContainer提供对请求模型的访问。
     * WebDataBinderFactory提供了一种在需要进行数据绑定和类型转换时创建WebDataBinder实例的方法。
    * @Param:
     *          parameter–要解析的方法参数。 此参数必须先前已传递给supportsParameter ，后者必须返回true 。
     *          mavContainer –当前请求的ModelAndViewContainer
     *          webRequest –当前请求
     *          bindingFactory –用于创建WebDataBinder实例的工厂
    * @return: 解析的参数值；如果无法解析，则返回null
    * @Author: lishuaiyun
    * @Date: 2021/5/10
    */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);


        return UserContext.getUser();
    }
}
