package com.imooc.common;

import com.imooc.object.User;

import java.beans.PropertyEditorSupport;

/**
 * @ClassName: MyPropertyEditor
 * @Description:  PropertyEditor实现数据绑定，类型转换
 * @Author: liuhefei
 * @Date: 2018/12/15
 * @blog: https://www.imooc.com/u/1323320/articles
 **/
public class MyPropertyEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        User u = new User();
        String[] textArray = text.split(",");
        u.setName(textArray[0]);
        u.setAge(Integer.parseInt(textArray[1]));
        this.setValue(u);
    }

    public static void main(String[] args) {
        MyPropertyEditor editor = new MyPropertyEditor();
        editor.setAsText("tom,22");
        System.out.println(editor.getValue());
    }
}
