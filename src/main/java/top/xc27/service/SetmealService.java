package top.xc27.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.xc27.common.R;
import top.xc27.entity.Setmeal;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 17108
* @description 针对表【setmeal(套餐)】的数据库操作Service
* @createDate 2022-10-22 00:30:38
*/
public interface SetmealService extends IService<Setmeal> {

    R<Page<Setmeal>> getPage(Setmeal setmeal);
}
