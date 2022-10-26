package top.xc27.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.xc27.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 17108
* @description 针对表【user(用户信息)】的数据库操作Mapper
* @createDate 2022-10-26 21:42:09
* @Entity top.xc27.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}




