package top.xc27.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xc27.entity.MyRuntimeException;
import top.xc27.entity.User;
import top.xc27.service.UserService;
import top.xc27.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 17108
* @description 针对表【user(用户信息)】的数据库操作Service实现
* @createDate 2022-10-26 21:42:09
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Override
    public User getUserByEmail(String email) {
        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getEmail, email).eq(User::getStatus, 1));
        if(ObjUtil.isEmpty(user)){
            throw new MyRuntimeException("没有查询到对应用户!");
        }
        return user;
    }
}




