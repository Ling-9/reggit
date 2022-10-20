package top.xc27.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.xc27.common.R;
import top.xc27.entity.Employee;
import top.xc27.entity.MyRuntimeException;
import top.xc27.service.IEmployeeService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("employee")
public class EmployeeController {

    @Autowired
    IEmployeeService employeeService;

    @PostMapping("login")
    public R<Employee> login(@RequestBody Employee employee, HttpServletRequest request){
       return employeeService.login(employee,request);
    }

    @PostMapping("logout")
    public R<String> logout(HttpServletRequest request){
        // 清除session
        request.getSession().removeAttribute("employee");
        return R.success("退出成功!");
    }

    @PostMapping
    public R<String> addEmployee(@RequestBody Employee employee, HttpServletRequest request){
        return employeeService.addEmployee(employee,request);
    }

    @GetMapping("/{id}")
    public R<Employee> queryEmployee(@PathVariable Long id){
        return employeeService.queryEmployeeById(id);
    }

    @PutMapping
    public R<String> editEmployee(@RequestBody Employee employee){
        return employeeService.editEmployee(employee);
    }

    @PostMapping("page")
    public R<Page<Employee>> getEmployees(@RequestBody Employee employee) {
        return employeeService.getPage(employee);
    }

}
