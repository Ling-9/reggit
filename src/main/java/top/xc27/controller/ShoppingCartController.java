package top.xc27.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xc27.common.R;
import top.xc27.entity.ShoppingCart;
import top.xc27.service.ShoppingCartService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("shoppingCart")
public class ShoppingCartController {

    @Autowired
    ShoppingCartService shoppingCartService;

    @GetMapping("list")
    public R<List<ShoppingCart>> getShoppingCarts(){
        return shoppingCartService.getShoppingCarts();
    }

    @PostMapping("add")
    public R<String> saveShoppingCart(@RequestBody ShoppingCart shoppingCart, HttpServletRequest request){
        return shoppingCartService.saveShoppingCart(shoppingCart,request);
    }

    @DeleteMapping("clean")
    public R<String> deleteShoppingCart(HttpServletRequest request){
        return shoppingCartService.deleteShoppingCart(request);
    }
}
