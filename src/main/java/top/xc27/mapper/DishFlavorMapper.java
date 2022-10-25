package top.xc27.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.xc27.entity.DishFlavor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Administered
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Mapper
* @createDate 2022-10-25 15:38:48
* @Entity top.xc27.entity.DishFlavor
*/
@Mapper
public interface DishFlavorMapper extends BaseMapper<DishFlavor> {

}




