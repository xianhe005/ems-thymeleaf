package com.hxh.emsthymeleaf.entity;

import java.util.Date;

public class Employee {

    private Integer id;
    private String name;
    private Double salary;
    //spring中接收的日期格式为：yyyy/MM/dd
    private Date birthday;
    private String photo; //头像

    public Employee() {
    }

    public Employee(Integer id, String name, Double salary, Date birthday, String photo) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.birthday = birthday;
        this.photo = photo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", birthday=" + birthday +
                ", photo='" + photo + '\'' +
                '}';
    }
}
