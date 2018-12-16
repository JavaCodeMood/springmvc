package com.imooc.object;

import java.util.Map;

/**
 * @ClassName: UserMapForm
 * @Description:  将user封装为map对象
 * @Author: liuhefei
 * @Date: 2018/12/15
 * @blog: https://www.imooc.com/u/1323320/articles
 **/
public class UserMapForm {
    private Map<String,User> users;

    @Override
    public String toString() {
        return "UserMapForm{" +
                "users=" + users +
                '}';
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void setUsers(Map<String, User> users) {
        this.users = users;
    }
}
