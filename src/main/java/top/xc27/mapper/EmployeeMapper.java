package top.xc27.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.xc27.entity.Employee;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
