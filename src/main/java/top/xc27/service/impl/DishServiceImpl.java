package top.xc27.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xc27.common.R;
import top.xc27.entity.Dish;
import top.xc27.service.DishService;
import top.xc27.mapper.DishMapper;
import org.springframework.stereotype.Service;

/**
* @author 17108
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2022-10-21 21:21:15
*/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService{

    @Override
    public R<Page<Dish>> getPage(Dish dish) {
        LambdaQueryWrapper<Dish> wrapper = queryWrapper(dish);
        return R.success(this.page(new Page<>(dish.getPage(),dish.getPageSize()),wrapper));
    }

    @Override
    public R<Dish> getDishById(Long id) {
        if(null == id){
            return R.error("id参数缺失!");
        }
        Dish dish = this.getOne(new LambdaQueryWrapper<Dish>().eq(Dish::getId, id));
        if(ObjUtil.isEmpty(dish)){
            return R.error("没有查询到对应菜品");
        }
        return R.success(dish);
    }

    private LambdaQueryWrapper<Dish> queryWrapper(Dish dish) {
        LambdaQueryWrapper<Dish> query = Wrappers.lambdaQuery();

        query.orderByAsc(Dish::getSort);
        return query;
    }
}




