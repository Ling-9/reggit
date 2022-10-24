package top.xc27.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import top.xc27.common.R;
import top.xc27.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author 17108
* @description 针对表【category(菜品及套餐分类)】的数据库操作Service
* @createDate 2022-10-21 20:25:39
*/
public interface CategoryService extends IService<Category> {

    R<Page<Category>> getPage(Category category);

    R<String> saveCategory(Category category);

    R<String> editCategory(Category category);

    R<String> deleteCategory(Long id);

    R<List<Category>> getList(Category category);

    Category getCategoryById(Long id);
}
