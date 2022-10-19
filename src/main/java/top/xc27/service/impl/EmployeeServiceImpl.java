package top.xc27.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import top.xc27.common.R;
import top.xc27.entity.Employee;
import top.xc27.entity.MyRuntimeException;
import top.xc27.mapper.EmployeeMapper;
import top.xc27.service.IEmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper,Employee> implements IEmployeeService {

    @Override
    public R<Employee> login(Employee employee, HttpServletRequest request) {
        String username = employee.getUsername();
        String password = employee.getPassword();
        if(StrUtil.isAllBlank(username,password)){
            return R.error("用户参数缺失!");
        }
        Employee user = getEmployeeByUserName(username);
        if(ObjUtil.isEmpty(user)){
            return R.error("用户不存在!");
        }
        String md5PassWord = DigestUtils.md5DigestAsHex(password.getBytes());
        if(!StrUtil.equals(md5PassWord,user.getPassword())){
            return R.error("密码错误!");
        }
        if(0 == user.getStatus()){
            return R.error("用户被禁用,请联系管理员!");
        }
        user.setPassword("");
        request.getSession().setAttribute("employee",user.getId());
        return R.success(user);
    }

    @Override
    public Employee getEmployeeByUserName(String userName) {
        return this.getOne(new LambdaQueryWrapper<Employee>().eq(Employee::getUsername, userName));
    }

    @Override
    public R<List<Employee>> getPage(Employee employee) {
        LambdaQueryWrapper<Employee> query = queryWrapper(employee);
        Page<Employee> page = this.page(new Page<>(employee.getPage(), employee.getPageSize()), query);
        return R.success(page.getRecords());
    }

    @Override
    public R<String> addEmployee(Employee employee, HttpServletRequest request) {
        Long id;
        try {
            id = (Long) request.getSession().getAttribute("employee");
        }catch (Exception e){
            throw new MyRuntimeException("获取登录用户ID失败!");
        }
        Employee crtEmployee = crtEmployee(employee,id);
        this.save(crtEmployee);
        return R.success("新增用户成功!");
    }

    private Employee crtEmployee(Employee employee,Long id) {
        Employee emp = new Employee();
        BeanUtil.copyProperties(employee,emp);
        emp.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        emp.setId(IdUtil.getSnowflakeNextId());
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        emp.setCreateUser(id);
        emp.setUpdateUser(id);
        emp.setStatus(1);
        return emp;
    }

    private LambdaQueryWrapper<Employee> queryWrapper(Employee employee) {
        LambdaQueryWrapper<Employee> query = Wrappers.lambdaQuery();
        if(StrUtil.isNotBlank(employee.getUsername())){
            query.eq(Employee::getUsername,employee.getUsername());
        }
        return query;
    }
}
