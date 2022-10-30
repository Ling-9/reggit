package top.xc27.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xc27.common.EmailUtil;
import top.xc27.common.R;
import top.xc27.common.RedisUtil;
import top.xc27.entity.MyRuntimeException;
import top.xc27.entity.User;
import top.xc27.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    EmailUtil emailUtil;
    @Autowired
    UserService userService;
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("getCode")
    public R<String> getCode(@RequestBody User user){
        if(StrUtil.isEmpty(user.getEmail())){
            R.error("邮箱参数缺失!");
        }
        try {
            emailUtil.sendCodeMessage(user);
        }catch (MyRuntimeException e){
            return R.error(e.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            redisUtil.clean(user.getEmail() + "code");
            throw new MyRuntimeException("发送邮件失败!");
        }
        return R.success("验证码发送成功!");
    }

    @PostMapping("login")
    public R<User> login(@RequestBody User user, HttpServletRequest request){
        System.out.println("user = " + user);
        if(StrUtil.isEmpty(user.getEmail()) || StrUtil.isEmpty(user.getCode())){
            return R.error("参数缺失!");
        }
        return userService.login(user, request);
    }

    @PostMapping("loginout")
    public R<String> logout(HttpServletRequest request){
        // 清除session
        request.getSession().removeAttribute("user");
        return R.success("退出成功!");
    }
}
