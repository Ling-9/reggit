package top.xc27.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xc27.common.R;
import top.xc27.entity.MyRuntimeException;
import top.xc27.entity.ShoppingCart;
import top.xc27.service.ShoppingCartService;
import top.xc27.mapper.ShoppingCartMapper;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
* @author 17108
* @description 针对表【shopping_cart(购物车)】的数据库操作Service实现
* @createDate 2022-10-29 16:54:00
*/
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService{

    @Override
    public R<List<ShoppingCart>> getShoppingCarts() {
        return R.success(this.list());
    }

    @Override
    public R<String> saveShoppingCart(ShoppingCart shoppingCart, HttpServletRequest request) {
        Long id = 0L;
        shoppingCart.setId(IdUtil.getSnowflakeNextId());
        shoppingCart.setCreateTime(LocalDateTime.now());
        try {
            id = (Long) request.getSession().getAttribute("user");
        }catch (Exception e){
            throw new MyRuntimeException("获取用户失败!");
        }
        ShoppingCart cart = getShoppingCartByDishId(shoppingCart.getDishId());
        if(ObjUtil.isNotEmpty(cart)){
            cart.setUserId(id);
            Integer number = cart.getNumber();
            cart.setNumber(++number);
            this.updateById(cart);
        }else {
            shoppingCart.setUserId(id);
            this.save(shoppingCart);
        }
        return R.success("加入购物车成功!");
    }

    @Override
    public R<String> deleteShoppingCart(HttpServletRequest request) {
        Long id = (Long) request.getSession().getAttribute("user");
        if(null == id){
            return R.error("用户id参数缺失!");
        }
        List<ShoppingCart> shoppingCarts =  getShoppingCartsByUserId(id);
        List<Long> ids = shoppingCarts.stream().map(ShoppingCart::getId).collect(Collectors.toList());
        this.removeByIds(ids);
        return R.success("清除购物车成功!");
    }

    @Override
    public R<String> removeShoppingCart(ShoppingCart shoppingCart) {
        if(ObjUtil.isNotEmpty(shoppingCart.getDishId())){
            ShoppingCart dish = getShoppingCartByDishId(shoppingCart.getDishId());
            Integer number = dish.getNumber();
            dish.setNumber(--number);
            this.updateById(dish);
        }
        return R.success("减少商品成功!");
    }

    public List<ShoppingCart> getShoppingCartsByUserId(Long id) {
        LambdaQueryWrapper<ShoppingCart> queryWrapper = Wrappers.lambdaQuery();
        LambdaQueryWrapper<ShoppingCart> wrapper = queryWrapper.eq(ShoppingCart::getUserId, id);
        return this.list(wrapper);
    }

    private ShoppingCart getShoppingCartByDishId(Long dishId){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = Wrappers.lambdaQuery();
        LambdaQueryWrapper<ShoppingCart> wrapper = queryWrapper.eq(ShoppingCart::getDishId, dishId);
        return this.getOne(wrapper);
    }

    private LambdaQueryWrapper<ShoppingCart> queryWrapper(ShoppingCart shoppingCart){
        LambdaQueryWrapper<ShoppingCart> queryWrapper = Wrappers.lambdaQuery();

        return queryWrapper;
    }
}




