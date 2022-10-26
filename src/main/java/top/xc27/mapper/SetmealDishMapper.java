package top.xc27.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.xc27.entity.SetmealDish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Administered
* @description 针对表【setmeal_dish(套餐菜品关系)】的数据库操作Mapper
* @createDate 2022-10-26 09:56:46
* @Entity top.xc27.entity.SetmealDish
*/
@Mapper
public interface SetmealDishMapper extends BaseMapper<SetmealDish> {

}




