package top.xc27.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class BasePage {

    @TableField(exist = false)
    private Integer page;
    @TableField(exist = false)
    private Integer pageSize;
}
