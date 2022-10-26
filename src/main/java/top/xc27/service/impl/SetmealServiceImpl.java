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
import top.xc27.entity.MyRuntimeException;
import top.xc27.entity.Setmeal;
import top.xc27.entity.SetmealDish;
import top.xc27.service.CategoryService;
import top.xc27.service.SetmealDishService;
import top.xc27.service.SetmealService;
import top.xc27.mapper.SetmealMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
* @author 17108
* @description 针对表【setmeal(套餐)】的数据库操作Service实现
* @createDate 2022-10-22 00:30:38
*/
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService{

    @Autowired
    CategoryService categoryService;
    @Autowired
    SetmealDishService setmealDishService;

    @Override
    public R<Page<Setmeal>> getPage(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> query = queryWrapper(setmeal);
        Page<Setmeal> page = this.page(new Page<>(setmeal.getPage(), setmeal.getPageSize()), query);
        return R.success(page);
    }

    @Override
    @Transactional
    public R<String> saveSetmeal(Setmeal setmeal) {
        if(ObjUtil.isEmpty(setmeal.getCategoryId())){
            return R.error("id参数缺失!");
        }
        long id = IdUtil.getSnowflakeNextId();
        List<SetmealDish> setmealDishes = setmeal.getSetmealDishes();
        setmealDishes.forEach(el->{
            el.setId(IdUtil.getSnowflakeNextId());
            el.setSetmealId(String.valueOf(id));
            if(!setmealDishService.save(el)){
                throw new MyRuntimeException("新增套餐菜品失败!");
            }
        });
        setmeal.setId(id);
        if(!this.save(setmeal)){
            throw new MyRuntimeException("新增套餐失败!");
        }
        return R.success("新增套餐成功!");
    }

    @Override
    public R<Setmeal> getSetmealById(Long id) {
        if(ObjUtil.isEmpty(id)){
            return R.error("id参数缺失!");
        }
        List<SetmealDish> setmealDishS = setmealDishService.getBySetmealId(id);
        Setmeal setmeal = new Setmeal();
        setmeal.setId(id);
        LambdaQueryWrapper<Setmeal> queryWrapper = queryWrapper(setmeal);
        Setmeal setmeal1 = this.getOne(queryWrapper);
        setmeal1.setSetmealDishes(setmealDishS);
        return R.success(setmeal1);
    }

    @Override
    @Transactional
    public R<String> editSetmeal(Setmeal setmeal) {
        if(ObjUtil.isEmpty(setmeal.getId())){
            return R.error("id参数缺失!");
        }
        List<SetmealDish> setmealDishes = setmealDishService.getBySetmealId(setmeal.getId());
        List<Long> ids = setmealDishes.stream().map(SetmealDish::getId).collect(Collectors.toList());
        setmealDishService.removeByIds(ids);
        List<SetmealDish> dishes = setmeal.getSetmealDishes();
        dishes.forEach(el->{
            el.setSetmealId(String.valueOf(setmeal.getId()));
            el.setId(IdUtil.getSnowflakeNextId());
            setmealDishService.save(el);
        });
        this.updateById(setmeal);
        return R.success("修改成功!");
    }

    @Override
    public R<String> editStatus(String ids, Integer status) {
        if(StrUtil.isEmpty(ids) || null == status){
            return R.error("id参数缺失!");
        }
        if(ids.contains(",")){
            String[] split = ids.split(",");
            for (String id : split) {
                Setmeal data = getSetmealById(Long.valueOf(id)).getData();
                data.setStatus(status);
                this.updateById(data);
            }
        }else {
            Setmeal data = getSetmealById(Long.valueOf(ids)).getData();
            data.setStatus(status);
            this.updateById(data);
        }
        return R.success("停售套餐成功!");
    }

    @Override
    public R<String> deleteSetmeal(String ids) {
        if(StrUtil.isEmpty(ids)){
            return R.error("id参数缺失!");
        }
        if(ids.contains(",")){
            int count = 0;
            StringBuilder stringBuilder = new StringBuilder();
            String[] split = ids.split(",");
            for (String id : split) {
                Setmeal data = getSetmealById(Long.valueOf(id)).getData();
                if(0 == data.getStatus()){
                    data.setIsDeleted(1);
                    this.updateById(data);
                }else {
                    ++ count;
                    stringBuilder.append(data.getName()).append(",");
                }
            }
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
            if(count != 0){
                return R.error("当前选择套餐{" + stringBuilder + "}正在售卖不能删除!");
            }
        }else {
            Setmeal data = getSetmealById(Long.valueOf(ids)).getData();
            if(0 == data.getStatus()){
                data.setIsDeleted(1);
                this.updateById(data);
            }else {
                return R.error("当前套餐{" + data.getName() + "}正在售卖不能删除!");
            }
        }
        return R.success("删除套餐成功!");
    }

    private LambdaQueryWrapper<Setmeal> queryWrapper(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> query = Wrappers.lambdaQuery();
        if(ObjUtil.isNotEmpty(setmeal.getId())){
            query.eq(Setmeal::getId,setmeal.getId());
        }
        query.eq(Setmeal::getIsDeleted,0);
        return query;
    }
}




