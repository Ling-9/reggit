package top.xc27.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xc27.common.R;
import top.xc27.entity.Dish;
import top.xc27.entity.Setmeal;
import top.xc27.service.SetmealService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @PostMapping("page")
    public R<Page<Setmeal>> getEmployees(@RequestBody Setmeal setmeal) {
        return setmealService.getPage(setmeal);
    }

    @PostMapping
    public R<String> saveSetmeal(@RequestBody Setmeal setmeal){
        return setmealService.saveSetmeal(setmeal);
    }

    @GetMapping("/{id}")
    public R<Setmeal> getSetmealById(@PathVariable Long id){
        return setmealService.getSetmealById(id);
    }

    @PutMapping
    public R<String> editSetmeal(@RequestBody Setmeal setmeal){
        return setmealService.editSetmeal(setmeal);
    }

    @PostMapping("status")
    public R<String> editStatus(String ids,Integer status){
        return setmealService.editStatus(ids,status);
    }

    @GetMapping("list")
    public R<List<Dish>> getSetmeals(String categoryId, String status){
        return setmealService.getSetmealDishs(categoryId,status);
    }

    @DeleteMapping
    public R<String> deleteSetmeal(String ids){
        return setmealService.deleteSetmeal(ids);
    }
}
