package top.xc27.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.xc27.common.R;
import top.xc27.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author 17108
* @description 针对表【orders(订单表)】的数据库操作Service
* @createDate 2022-10-22 00:36:22
*/
public interface OrdersService extends IService<Orders> {

    R<Page<Orders>> getPage(Orders orders);

    R<String> saveOrder(Orders orders, HttpServletRequest request);
}
