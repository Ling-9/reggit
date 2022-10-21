package top.xc27.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xc27.common.R;
import top.xc27.entity.Category;
import top.xc27.service.CategoryService;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> saveCategory(@RequestBody Category category){
        return categoryService.saveCategory(category);
    }

    @PostMapping("page")
    public R<Page<Category>> page (@RequestBody Category category){
        return categoryService.getPage(category);
    }

    @PutMapping
    public R<String> editCategory(@RequestBody Category category){
        return categoryService.editCategory(category);
    }

    @DeleteMapping
    public R<String> deleteCategory(Long id){
        return categoryService.deleteCategory(id);
    }

    @GetMapping("list")
    public R<List<Category>> listCategory(Category category){
        return categoryService.getList(category);
    }
}
