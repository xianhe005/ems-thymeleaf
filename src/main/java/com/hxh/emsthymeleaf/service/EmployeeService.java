package com.hxh.emsthymeleaf.service;

import com.hxh.emsthymeleaf.entity.Employee;

import java.util.List;

public interface EmployeeService {
    /**
     * 查询员工列表
     */
    List<Employee> list();

    /**
     * 添加员工
     */
    void add(Employee employee);

    /**
     * 根据id查询员工信息
     */
    Employee idByEmployee(Integer id);


    /**
     * 更新员工信息
     */
    void update(Employee employee);

    /**
     * 根据id删除员工信息
     */
    void delete(Integer id);
}
