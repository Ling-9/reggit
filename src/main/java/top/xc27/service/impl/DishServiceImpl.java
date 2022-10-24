package top.xc27.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import top.xc27.common.R;
import top.xc27.entity.Category;
import top.xc27.entity.Dish;
import top.xc27.service.CategoryService;
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

    @Autowired
    private CategoryService categoryService;

    @Override
    public R<Page<Dish>> getPage(Dish dish) {
        LambdaQueryWrapper<Dish> wrapper = queryWrapper(dish);
        Page<Dish> page = this.page(new Page<>(dish.getPage(), dish.getPageSize()), wrapper);
        page.getRecords().forEach(el->{
            Category category = categoryService.getCategoryById(el.getCategoryId());
            el.setCategoryName(category.getName());
        });
        return R.success(page);
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

    @Override
    public R<String> saveDish(Dish dish) {
        dish.setId(IdUtil.getSnowflakeNextId());
        if(!this.save(dish)){
            return R.error("新增菜品失败!");
        }
        return R.success("新增菜品成功!");
    }

    @Override
    public R<String> deleteDish(Long ids) {
        if(null == ids){
            return R.error("id参数缺失!");
        }
        if(!this.removeById(ids)){
            return R.error("删除菜品失败!");
        }
        return R.success("删除菜品成功!");
    }

    private LambdaQueryWrapper<Dish> queryWrapper(Dish dish) {
        LambdaQueryWrapper<Dish> query = Wrappers.lambdaQuery();
        if(StrUtil.isNotEmpty(dish.getName())){
            query.like(Dish::getName,dish.getName());
        }
        query.orderByAsc(Dish::getCreateTime);
        return query;
    }
}




