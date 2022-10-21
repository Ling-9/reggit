package top.xc27.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class BaseEntity {

    @TableField(exist = false)
    private Integer page;

    @TableField(exist = false)
    private Integer pageSize;

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
