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
    public R<Page<Employee>> getPage(Employee employee) {
        LambdaQueryWrapper<Employee> query = queryWrapper(employee);
        Page<Employee> page = this.page(new Page<>(employee.getPage(), employee.getPageSize()), query);
        page.getRecords().forEach(el->{
            el.setPassword("");
        });
        return R.success(page);
    }

    @Override
    public R<String> saveEmployee(Employee employee) {
        crtEmployee(employee);
        if(!this.save(employee)){
            return R.error("新增用户失败!");
        }
        return R.success("新增用户成功!");
    }

    @Override
    public R<Employee> queryEmployeeById(Long id) {
        Employee employee = this.getOne(new LambdaQueryWrapper<Employee>()
                .eq(Employee::getId, id));
        if(ObjUtil.isEmpty(employee)){
            throw new MyRuntimeException("没有查询到对应用户信息!");
        }
        return R.success(employee);
    }

    @Override
    public R<Employee> queryEmployeeById(String id) {
        Employee employee = this.getOne(new LambdaQueryWrapper<Employee>()
                .eq(Employee::getId, id));
        if(ObjUtil.isEmpty(employee)){
            throw new MyRuntimeException("没有查询到对应用户信息!");
        }
        return R.success(employee);
    }

    @Override
    public R<String> editEmployee(Employee employee) {
        String id = employee.getId() + "";
        if(StrUtil.isEmpty(id)){
            throw new MyRuntimeException("id参数缺失!");
        }
        if(!this.updateById(employee)){
            return R.error("修改用户失败!");
        }else {
            return R.success("修改用户成功!");
        }
    }

    private void crtEmployee(Employee employee) {
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setId(IdUtil.getSnowflakeNextId());
        employee.setStatus(1);
    }

    private LambdaQueryWrapper<Employee> queryWrapper(Employee employee) {
        LambdaQueryWrapper<Employee> query = Wrappers.lambdaQuery();
        if(StrUtil.isNotBlank(employee.getName())){
            query.eq(Employee::getName,employee.getName());
        }
        query.orderByDesc(Employee::getCreateTime);
        return query;
    }
}
