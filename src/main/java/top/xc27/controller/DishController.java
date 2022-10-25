package top.xc27.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xc27.common.R;
import top.xc27.entity.Dish;
import top.xc27.service.DishService;

@Slf4j
@RestController
@RequestMapping("dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping("page")
    public R<Page<Dish>> getPage(@RequestBody Dish dish){
        return dishService.getPage(dish);
    }

    @GetMapping("/{id}")
    public R<Dish> getDishById(@PathVariable Long id){
        return dishService.getDishById(id);
    }

    @PostMapping
    public R<String> saveDish(@RequestBody Dish dish){
        return dishService.saveDish(dish);
    }

    @DeleteMapping
    public R<String> deleteDish(String ids){
        return dishService.deleteDish(ids);
    }

    @PostMapping("status")
    public R<String> editDishStatus(String ids,Integer status){
        return dishService.editDishStatus(ids,status);
    }

    @PutMapping
    public R<String> editDish(@RequestBody Dish dish){
        return dishService.editDish(dish);
    }
}
