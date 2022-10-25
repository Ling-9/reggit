package top.xc27.service;

import top.xc27.entity.Dish;
import top.xc27.entity.DishFlavor;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Administered
* @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service
* @createDate 2022-10-25 15:38:48
*/
public interface DishFlavorService extends IService<DishFlavor> {

    List<DishFlavor> getDishFlavorsById(Long id);

    void saveDishFlavor(Dish dish);
}
