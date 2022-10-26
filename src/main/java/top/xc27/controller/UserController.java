package top.xc27.controller;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xc27.common.EmailUtil;
import top.xc27.common.R;
import top.xc27.common.RedisUtil;
import top.xc27.entity.MyRuntimeException;
import top.xc27.entity.User;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    EmailUtil emailUtil;
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
            redisUtil.clean("code");
            throw new MyRuntimeException("发送邮件失败!");
        }
        return R.success("验证码发送成功!");
    }

    @PostMapping("login")
    public R<String> login(@RequestBody User user){
        System.out.println("user = " + user);
        return null;
    }
}
