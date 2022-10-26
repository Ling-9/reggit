package top.xc27.common;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import top.xc27.entity.MyRuntimeException;
import top.xc27.entity.User;
import top.xc27.service.UserService;

@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String username;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserService userService;

    public void sendMessage(String to, String subject, String content) {
        // 创建一个邮件对象
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(username); // 设置发送人地址
        msg.setTo(to); // 设置接收方
        msg.setSubject(subject); // 设置邮件主题
        msg.setText(content); // 设置邮件内容
        // 发送邮件
        mailSender.send(msg);
    }

    public void sendCodeMessage(User user) {
        User one = userService.getUserByEmail(user.getEmail());
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        String text = lineCaptcha.getCode();
        if(redisUtil.hasKey("code")){
            throw new MyRuntimeException("当前验证码还未失效,请检查垃圾箱或者被拦截邮件!");
        }
        redisUtil.setString("code",text,120);
        // 创建一个邮件对象
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(username); // 设置发送人地址
        msg.setTo(user.getEmail()); // 设置接收方
        msg.setSubject("注册验证码"); // 设置邮件主题
        msg.setText(one.getName() + ",你好:\n"  + "本次注册验证码是：" + text + "(有效期两分钟),请及时输入!"); // 设置邮件内容
        // 发送邮件
        mailSender.send(msg);
    }
}
