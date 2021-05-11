package com.mooc.miaosha.controller;

import com.mooc.miaosha.result.Result;
import com.mooc.miaosha.service.MiaoshaUserService;
import com.mooc.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    MiaoshaUserService userService;


    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    /**
    * @Description: 注意response对象只能在controller中才能注入，因为tomcat调用controller，
     * 只有tomcat有request和response。@Valid注解表明需要对该参数进行校验，此时会对该注解修饰的实体开启校验功能，
     * 检查属性上的校验规则
    * @Param: response对象，loginVo登录实体类
    * @return: 状态信息
    * @Author: lishuaiyun
    * @Date: 2021/5/7
    */
    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo){
        //记录日志信息
        log.info(loginVo.toString());
        //参数校验
//        String passInput = loginVo.getPassword();
//        String mobile = loginVo.getMobile();
//        if(StringUtils.isEmpty(passInput)){
//            return Result.error(CodeMsg.PASSWORD_EMPTY);
//        }
//        if(StringUtils.isEmpty(mobile)){
//            return Result.error(CodeMsg.MOBILE_EMPTY);
//        }
//        if(!ValidatorUtil.isMobile(mobile)){
//            //后通过注解Ismoubile替换
//            return Result.error(CodeMsg.MOBILE_ERROR);
//        }
        //登录
        userService.login(response,loginVo);
        return Result.success(true);
    }

}















