package top.xc27.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import top.xc27.common.R;
import top.xc27.entity.Setmeal;
import top.xc27.service.CategoryService;
import top.xc27.service.SetmealDishService;
import top.xc27.service.SetmealService;
import top.xc27.mapper.SetmealMapper;
import org.springframework.stereotype.Service;

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
    public R<String> saveSetmeal(Setmeal setmeal) {
        long id = IdUtil.getSnowflakeNextId();
        setmeal.setId(id);
        if(ObjUtil.isEmpty(setmeal.getCategoryId())){
            return R.error("id参数缺失!");
        }





        return null;
    }

    private LambdaQueryWrapper<Setmeal> queryWrapper(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> query = Wrappers.lambdaQuery();

        return query;
    }
}




