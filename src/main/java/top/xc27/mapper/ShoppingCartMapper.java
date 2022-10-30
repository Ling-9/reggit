package top.xc27.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.xc27.entity.ShoppingCart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 17108
* @description 针对表【shopping_cart(购物车)】的数据库操作Mapper
* @createDate 2022-10-29 16:54:00
* @Entity top.xc27.entity.ShoppingCart
*/
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {

}




