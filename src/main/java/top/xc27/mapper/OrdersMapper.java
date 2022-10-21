package top.xc27.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.xc27.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 17108
* @description 针对表【orders(订单表)】的数据库操作Mapper
* @createDate 2022-10-22 00:36:22
* @Entity top.xc27.entity.Orders
*/
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

}




