package top.xc27.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.xc27.common.R;
import top.xc27.entity.Dish;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 17108
* @description 针对表【dish(菜品管理)】的数据库操作Service
* @createDate 2022-10-21 21:21:15
*/
public interface DishService extends IService<Dish> {

    R<Page<Dish>> getPage(Dish dish);

    R<Dish> getDishById(Long id);

    R<String> saveDish(Dish dish);

    R<String> deleteDish(String ids);

    R<String> editDish(Dish dish);

    R<String> editDishStatus(String ids,Integer status);

    R<List<Dish>> listDishByCategoryId(Dish dish);
}
