package com.hxh.emsthymeleaf.controller;

import com.hxh.emsthymeleaf.entity.Employee;
import com.hxh.emsthymeleaf.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

// 员工模块
@Controller
@RequestMapping("employee")
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final EmployeeService employeeService;

    @Value("${photo.file.dir}")
    private String realpath;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * 员工列表
     */
    @RequestMapping("list")
    public String listEmployee(HttpServletRequest request, Model model) {
        List<Employee> list = employeeService.list();
        //request.setAttribute("employees", list);
        // 同上
        model.addAttribute("employees", list);
        return "emplist";
    }

    @RequestMapping("add")
    public String addEmployee(Employee employee, MultipartFile img) throws IOException {
        log.debug(employee.toString());
        String originalFilename = img.getOriginalFilename();
        log.debug("头像名称：{}", originalFilename);
        log.debug("头像大小：{}", img.getSize());
        log.debug("上传文件路径：{}", realpath);

        //1.上传头像
        String fileNamePrefix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = fileNamePrefix + fileNameSuffix;

        img.transferTo(new File(realpath, newFileName));

        //2.保存员工信息
        employee.setPhoto(newFileName);

        employeeService.add(employee);
        return "redirect:/employee/list"; // 保存成功
    }

    /**
     * 根据id查询员工信息
     */
    @RequestMapping("detail")
    public String detailEmployee(Integer id, Model model) {
        log.debug(String.valueOf(id));
        Employee employee = employeeService.idByEmployee(id);
        log.debug(employee.toString());
        model.addAttribute("employee", employee);
        return "updateEmp";
    }

    /**
     * 更新员工信息
     */
    @RequestMapping("update")
    public String updateEmployee(Employee employee, MultipartFile img) throws IOException {
        log.debug(employee.toString());
        String oldPhoto = employee.getPhoto();
        String originalFilename = img.getOriginalFilename();
        if (!StringUtils.isEmpty(originalFilename)) {
            //1.上传头像
            String fileNamePrefix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFileName = fileNamePrefix + fileNameSuffix;

            img.transferTo(new File(realpath, newFileName));
            employee.setPhoto(newFileName);
        }
        employeeService.update(employee);
        if (!StringUtils.isEmpty(originalFilename) && !StringUtils.isEmpty(oldPhoto)) {
            //noinspection ResultOfMethodCallIgnored
            new File(realpath, oldPhoto).delete();
        }
        return "redirect:/employee/list";
    }

    /**
     * 根据id删除员工信息
     */
    @RequestMapping("delete")
    public String deleteEmployee(Integer id, String photo) {
        log.debug(String.valueOf(id));
        log.debug(photo);
        /*Employee employee = employeeService.idByEmployee(id);*/
        employeeService.delete(id);
        if (!StringUtils.isEmpty(photo)) {
            //noinspection ResultOfMethodCallIgnored
            new File(realpath, photo).delete();
        }
        return "redirect:/employee/list";
    }
}
