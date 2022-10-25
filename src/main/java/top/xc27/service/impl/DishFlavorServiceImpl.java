package top.xc27.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xc27.entity.Dish;
import top.xc27.entity.DishFlavor;
import top.xc27.service.DishFlavorService;
import top.xc27.mapper.DishFlavorMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administered
 * @description 针对表【dish_flavor(菜品口味关系表)】的数据库操作Service实现
 * @createDate 2022-10-25 15:38:48
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {

    @Override
    public List<DishFlavor> getDishFlavorsById(Long id) {
        return this.list(new LambdaQueryWrapper<DishFlavor>().eq(DishFlavor::getDishId, id));
    }

    @Override
    public void saveDishFlavor(Dish dish) {
        Long dishId = dish.getId();
        if (0 != dish.getFlavors().size()) {
            dish.getFlavors().forEach(el -> {
                el.setDishId(dishId);
                el.setId(IdUtil.getSnowflakeNextId());
                if (!this.save(el)) {
                    throw new RuntimeException("新增菜品口味失败！");
                }
            });
        }
    }
}




