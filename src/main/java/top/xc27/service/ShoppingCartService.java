package top.xc27.service;

import top.xc27.common.R;
import top.xc27.entity.ShoppingCart;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 17108
* @description 针对表【shopping_cart(购物车)】的数据库操作Service
* @createDate 2022-10-29 16:54:00
*/
public interface ShoppingCartService extends IService<ShoppingCart> {

    R<List<ShoppingCart>> getShoppingCarts();

    R<String> saveShoppingCart(ShoppingCart shoppingCart, HttpServletRequest request);

    R<String> deleteShoppingCart(HttpServletRequest request);

    R<String> removeShoppingCart(ShoppingCart shoppingCart);

    public List<ShoppingCart> getShoppingCartsByUserId(Long id);
}
