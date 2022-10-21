package top.xc27.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xc27.common.R;
import top.xc27.entity.Orders;
import top.xc27.service.OrdersService;
import top.xc27.mapper.OrdersMapper;
import org.springframework.stereotype.Service;

/**
* @author 17108
* @description 针对表【orders(订单表)】的数据库操作Service实现
* @createDate 2022-10-22 00:36:22
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService{

    @Override
    public R<Page<Orders>> getPage(Orders orders) {
        LambdaQueryWrapper<Orders> query = queryWrapper(orders);
        Page<Orders> page = this.page(new Page<>(orders.getPage(), orders.getPageSize()), query);
        return R.success(page);
    }

    private LambdaQueryWrapper<Orders> queryWrapper(Orders orders) {
        LambdaQueryWrapper<Orders> query = Wrappers.lambdaQuery();

        return query;
    }
}




