package com.imooc.object;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @ClassName: UserSetForm
 * @Description:  将user封装为set对象
 * @Author: liuhefei
 * @Date: 2018/12/15
 * @blog: https://www.imooc.com/u/1323320/articles
 **/
public class UserSetForm {
    private Set<User> users;

    private UserSetForm(){
        users = new LinkedHashSet<User>();
        users.add(new User());
        users.add(new User());
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "UserSetForm{" +
                "users=" + users +
                '}';
    }
}
