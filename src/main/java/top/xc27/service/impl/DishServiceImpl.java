package top.xc27.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import top.xc27.common.R;
import top.xc27.entity.Category;
import top.xc27.entity.Dish;
import top.xc27.entity.DishFlavor;
import top.xc27.service.CategoryService;
import top.xc27.service.DishFlavorService;
import top.xc27.service.DishService;
import top.xc27.mapper.DishMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 17108
* @description 针对表【dish(菜品管理)】的数据库操作Service实现
* @createDate 2022-10-21 21:21:15
*/
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService{

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishFlavorService dishFlavorService;

    @Override
    public R<Page<Dish>> getPage(Dish dish) {
        LambdaQueryWrapper<Dish> wrapper = queryWrapper(dish);
        Page<Dish> page = this.page(new Page<>(dish.getPage(), dish.getPageSize()), wrapper);
        page.getRecords().forEach(el->{
            Category category = categoryService.getCategoryById(el.getCategoryId());
            if(ObjUtil.isNotEmpty(category)){
                el.setCategoryName(category.getName());
            }
        });
        return R.success(page);
    }

    @Override
    public R<Dish> getDishById(Long id) {
        if(null == id){
            return R.error("id参数缺失!");
        }
        Dish dish = this.getOne(new LambdaQueryWrapper<Dish>().eq(Dish::getId, id));
        List<DishFlavor> flavors = dishFlavorService.getDishFlavorsById(id);
        dish.setFlavors(flavors);
        if(ObjUtil.isEmpty(dish)){
            return R.error("没有查询到对应菜品");
        }
        return R.success(dish);
    }

    @Override
    @Transactional
    public R<String> saveDish(Dish dish) {
        long dishId = IdUtil.getSnowflakeNextId();
        dish.setId(dishId);
        if(!this.save(dish)){
            return R.error("新增菜品失败!");
        }
        dishFlavorService.saveDishFlavor(dish);
        return R.success("新增菜品成功!");
    }

    @Override
    @Transactional
    public R<String> deleteDish(String ids) {
        if(StrUtil.isEmpty(ids)){
            return R.error("id参数缺失!");
        }
        if(ids.contains(",")){
            String[] split = ids.split(",");
            LinkedList<String> lists = new LinkedList<>(Arrays.asList(split));
            for (String id : lists) {
                List<DishFlavor> flavors = dishFlavorService.getDishFlavorsById(Long.valueOf(id));
                for (DishFlavor flavor : flavors) {
                    flavor.setIsDeleted(1);
                    if(!dishFlavorService.updateById(flavor)){
                        throw new RuntimeException("删除对应菜品口味失败!" + flavor.getId());
                    }
                }
                Dish dish = getDishById(Long.valueOf(id)).getData();
                dish.setIsDeleted(1);
                if(!this.updateById(dish)){
                    return R.error("批量删除菜品失败!");
                }
            }
        }else {
            Dish data = getDishById(Long.valueOf(ids)).getData();
            if(ObjUtil.isNotEmpty(data)){
                data.setIsDeleted(1);
                if(!this.updateById(data)){
                    return R.error("删除菜品失败!");
                }
            }
            List<DishFlavor> dishFlavors = dishFlavorService.getDishFlavorsById(Long.valueOf(ids));
            dishFlavors.forEach(el->{
                el.setIsDeleted(1);
                if(!dishFlavorService.updateById(el)){
                    throw new RuntimeException("删除对应菜品口味失败!" + el.getId());
                }
            });
        }
        return R.success("删除菜品成功!");
    }

    @Override
    @Transactional
    public R<String> editDish(Dish dish) {
        Long id = dish.getId();
        if(ObjUtil.isEmpty(dish) || null == id){
            return R.error("参数缺失!");
        }
        if(!this.updateById(dish)){
            return R.error("更新菜品失败!");
        }
        try {
            dishFlavorService.removeByIds(dishFlavorService.getDishFlavorsById(id).stream().map(DishFlavor::getId).collect(Collectors.toList()));
            dishFlavorService.saveDishFlavor(dish);
        }catch (Exception e){
            return R.error("更新菜品口味失败!");
        }
        return R.success("更新菜品口味成功!");
    }

    @Override
    public R<String> editDishStatus(String ids,Integer status) {
        if(StrUtil.isEmpty(ids)){
            return R.error("id参数缺失!");
        }
        if(ids.contains(",")){
            String[] split = ids.split(",");
            LinkedList<String> lists = new LinkedList<>(Arrays.asList(split));
            for (String id : lists) {
                Dish dish = getDishById(Long.valueOf(id)).getData();
                dish.setStatus(status);
                if(!this.updateById(dish)){
                    return R.error("停售商品失败!");
                }
            }
        }else {
            Dish dish = getDishById(Long.valueOf(ids)).getData();
            dish.setStatus(status);
            if(!this.updateById(dish)){
                return R.error("停售商品失败!");
            }
        }
        return R.success("停售商品成功!");
    }

    @Override
    public R<List<Dish>> listDishByCategoryId(Dish dish) {
        if(null == dish.getCategoryId()){
            return R.error("id参数缺失!");
        }
        LambdaQueryWrapper<Dish> queryWrapper = queryWrapper(dish);
        List<Dish> list = this.list(queryWrapper);
        return R.success(list);
    }

    private LambdaQueryWrapper<Dish> queryWrapper(Dish dish) {
        LambdaQueryWrapper<Dish> query = Wrappers.lambdaQuery();
        if(StrUtil.isNotEmpty(dish.getName())){
            query.like(Dish::getName,dish.getName());
        }
        if(ObjUtil.isNotEmpty(dish.getId())){
            query.eq(Dish::getId,dish.getId());
        }
        if(ObjUtil.isNotEmpty(dish.getCategoryId())){
            query.eq(Dish::getCategoryId,dish.getCategoryId());
        }
        query.eq(Dish::getIsDeleted,0);
        query.orderByDesc(Dish::getCreateTime);
        return query;
    }
}




