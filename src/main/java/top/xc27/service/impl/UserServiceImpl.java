package top.xc27.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import top.xc27.common.R;
import top.xc27.common.RedisUtil;
import top.xc27.entity.MyRuntimeException;
import top.xc27.entity.User;
import top.xc27.service.UserService;
import top.xc27.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 17108
 * @description 针对表【user(用户信息)】的数据库操作Service实现
 * @createDate 2022-10-26 21:42:09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public User getUserByEmail(String email) {
        return this.getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email).eq(User::getStatus, 1));
    }

    @Override
    public R<User> login(User user, HttpServletRequest request) {
        String email = user.getEmail();
        String code = user.getCode();
        try {
            String redisCode = redisUtil.getString(user.getEmail() + "code");
            if (StrUtil.isNotEmpty(redisCode)) {
                if (StrUtil.equals(code, redisCode)) {
                    User userByEmail = getUserByEmail(email);
                    if (ObjUtil.isEmpty(userByEmail)) {
                        // 新建用户
                        long id = IdUtil.getSnowflakeNextId();
                        user.setId(id);
                        user.setName("默认名称");
                        user.setStatus(1);
                        this.save(user);
                        request.getSession().setAttribute("user",id);
                        return R.success(user);
                    } else {
                        // 登录
                        request.getSession().setAttribute("user",userByEmail.getId());
                        return R.success(userByEmail);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyRuntimeException("出现异常!");
        }finally {
            redisUtil.clean(user.getEmail() + "code");
        }
        return R.error("登录失败!");
    }
}




