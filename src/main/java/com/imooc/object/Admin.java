package com.imooc.object;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @ClassName: Admin
 * @Description:  admin对象
 * @Author: liuhefei
 * @Date: 2018/12/15
 * @blog: https://www.imooc.com/u/1323320/articles
 **/
@XmlRootElement(name="admin")
public class Admin {
    private String name;
    private Integer age;

    @XmlElement(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name="age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
