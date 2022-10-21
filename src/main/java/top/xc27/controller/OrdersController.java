package top.xc27.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xc27.common.R;
import top.xc27.entity.Orders;
import top.xc27.service.OrdersService;

@Slf4j
@RestController
@RequestMapping("order")
public class OrdersController {

    @Autowired
    OrdersService ordersService;

    @PostMapping("page")
    public R<Page<Orders>> getEmployees(@RequestBody Orders orders) {
        return ordersService.getPage(orders);
    }

}
