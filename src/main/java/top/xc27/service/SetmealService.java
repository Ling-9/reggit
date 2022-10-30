package top.xc27.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.xc27.common.R;
import top.xc27.entity.Dish;
import top.xc27.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 17108
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2022-10-22 00:30:38
*/
public interface SetmealService extends IService<Setmeal> {

    R<Page<Setmeal>> getPage(Setmeal setmeal);

    R<String> saveSetmeal(Setmeal setmeal);

    R<Setmeal> getSetmealById(Long id);

    R<String> editSetmeal(Setmeal setmeal);

    R<String> editStatus(String ids, Integer status);

    R<String> deleteSetmeal(String ids);

    R<List<Dish>> getSetmealDishs(String categoryId, String status);
}
