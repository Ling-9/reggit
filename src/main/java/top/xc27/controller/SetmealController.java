package top.xc27.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.xc27.common.R;
import top.xc27.entity.Setmeal;
import top.xc27.service.SetmealService;

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
}
