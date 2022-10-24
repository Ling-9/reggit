package top.xc27.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import top.xc27.common.R;
import top.xc27.entity.Category;
import top.xc27.entity.MyRuntimeException;
import top.xc27.service.CategoryService;
import top.xc27.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author 17108
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service实现
* @createDate 2022-10-21 20:25:39
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService{

    @Override
    public R<Page<Category>> getPage(Category category) {
        LambdaQueryWrapper<Category> wrapper = queryWrapper(category);
        return R.success(this.page(new Page<>(category.getPage(),category.getPageSize()),wrapper));
    }

    @Override
    public R<String> saveCategory(Category category) {
        category.setId(IdUtil.getSnowflakeNextId());
        if(!this.save(category)){
            return R.error("新增菜单分类失败!");
        }
        return R.success("新增菜单分类成功!");
    }

    @Override
    public R<String> editCategory(Category category) {
        if(null == category.getId()){
            throw new MyRuntimeException("id缺失!");
        }
        if(!this.updateById(category)){
            return R.error("修改菜单分类失败!");
        }
        return R.success("修改菜单分类成功!");
    }

    @Override
    public R<String> deleteCategory(Long id) {
        if(null == id){
            throw new MyRuntimeException("id缺失!");
        }
        if(!this.removeById(id)){
            return R.error("删除失败!");
        }
        return R.success("删除成功!");
    }

    @Override
    public R<List<Category>> getList(Category category) {
        LambdaQueryWrapper<Category> wrapper = queryWrapper(category);
        return R.success(this.list(wrapper));
    }

    @Override
    public Category getCategoryById(Long id) {
        return this.getOne(new LambdaQueryWrapper<Category>().eq(Category::getId,id));
    }

    private LambdaQueryWrapper<Category> queryWrapper(Category category) {
        LambdaQueryWrapper<Category> query = Wrappers.lambdaQuery();
        if(null != category.getType()){
            query.eq(Category::getType,category.getType());
        }
        query.orderByAsc(Category::getSort);
        return query;
    }

}




