package top.xc27.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.xc27.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 17108
* @description 针对表【category(菜品及套餐分类)】的数据库操作Mapper
* @createDate 2022-10-21 20:25:39
* @Entity top.xc27.entity.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




