package top.xc27.service.impl;

import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xc27.common.R;
import top.xc27.entity.MyRuntimeException;
import top.xc27.entity.SetmealDish;
import top.xc27.service.SetmealDishService;
import top.xc27.mapper.SetmealDishMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Administered
* @description 针对表【setmeal_dish(套餐菜品关系)】的数据库操作Service实现
* @createDate 2022-10-26 09:56:46
*/
@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService{

    @Override
    public List<SetmealDish> getBySetmealId(Long id) {
        if(ObjUtil.isEmpty(id)){
            throw new MyRuntimeException("id参数缺失!");
        }
        return this.list(new LambdaQueryWrapper<SetmealDish>().eq(SetmealDish::getSetmealId,id));
    }

}




