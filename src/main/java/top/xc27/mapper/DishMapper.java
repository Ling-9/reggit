package top.xc27.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.xc27.entity.Dish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 17108
* @description 针对表【dish(菜品管理)】的数据库操作Mapper
* @createDate 2022-10-21 21:21:15
* @Entity top.xc27.entity.Dish
*/
@Mapper
public interface DishMapper extends BaseMapper<Dish> {

}




