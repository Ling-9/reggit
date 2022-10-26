package top.xc27.service;

import top.xc27.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 17108
* @description 针对表【user(用户信息)】的数据库操作Service
* @createDate 2022-10-26 21:42:09
*/
public interface UserService extends IService<User> {

    User getUserByEmail(String email);
}
