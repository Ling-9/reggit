package top.xc27.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import top.xc27.common.R;
import top.xc27.entity.Orders;
import top.xc27.entity.ShoppingCart;
import top.xc27.service.OrdersService;
import top.xc27.mapper.OrdersMapper;
import org.springframework.stereotype.Service;
import top.xc27.service.ShoppingCartService;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
* @author 17108
* @description 针对表【orders(订单表)】的数据库操作Service实现
* @createDate 2022-10-22 00:36:22
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService{

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Override
    public R<Page<Orders>> getPage(Orders orders) {
        LambdaQueryWrapper<Orders> query = queryWrapper(orders);
        Page<Orders> page = this.page(new Page<>(orders.getPage(), orders.getPageSize()), query);
        return R.success(page);
    }

    @Override
    public R<String> saveOrder(Orders orders, HttpServletRequest request) {
        BigDecimal bigDecimal = new BigDecimal(0);
        long nextId = IdUtil.getSnowflakeNextId();
        long numId = IdUtil.getSnowflakeNextId();
        orders.setId(nextId);
        orders.setNumber(String.valueOf(numId));
        orders.setStatus(1);
        Long userId = (Long) request.getSession().getAttribute("user");
        orders.setUserId(userId);
        orders.setAddressBookId(orders.getAddressBookId());
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setPayMethod(orders.getPayMethod());

        List<ShoppingCart> carts = shoppingCartService.getShoppingCartsByUserId(userId);
        carts.forEach(el->{
            bigDecimal.add(el.getAmount());
        });
        orders.setAmount(bigDecimal);
        this.save(orders);
        shoppingCartService.deleteShoppingCart(request);
        return R.success("下单成功!");
    }

    private LambdaQueryWrapper<Orders> queryWrapper(Orders orders) {
        LambdaQueryWrapper<Orders> query = Wrappers.lambdaQuery();

        return query;
    }
}




