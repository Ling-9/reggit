package top.xc27.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xc27.common.R;
import top.xc27.entity.Orders;
import top.xc27.service.OrdersService;

import javax.servlet.http.HttpServletRequest;

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

    @PostMapping("submit")
    public R<String> saveOrder(@RequestBody Orders orders, HttpServletRequest request) {
        return ordersService.saveOrder(orders,request);
    }
}
