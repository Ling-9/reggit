package top.xc27.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import top.xc27.common.R;
import top.xc27.entity.Employee;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface IEmployeeService extends IService<Employee> {

    R<Employee> login(Employee employee, HttpServletRequest request);

    Employee getEmployeeByUserName(String userName);

    R<Page<Employee>> getPage(Employee employee);

    R<String> addEmployee(Employee employee, HttpServletRequest request);

    R<Employee> queryEmployeeById(Long id);

    R<Employee> queryEmployeeById(String id);

    R<String> editEmployee(Employee employee);
}
