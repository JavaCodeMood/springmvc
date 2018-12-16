package com.imooc.controller;

import com.imooc.object.*;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @ClassName: TestController
 * @Description:  SpringMVC绑定基本数据类型
 * @Author: liuhefei
 * @Date: 2018/12/15
 * @blog: https://www.imooc.com/u/1323320/articles
 **/
@Controller
public class TestController {

    /**
     * 基本数据类型的绑定：key是必须传的；只能是该基本数据类型
     * 基本类型不可接受空值，会报错。且输入数值必须在指定数据类型的数据范围内
     * @param age
     * @return
     */
    //todo     http://localhost:8080/baseType.do?age=10
    //todo 500 http://localhost:8080/baseType.do
    //todo 400 http://localhost:8080/baseType.do?age=abc
    @RequestMapping(value = "baseType.do")
    @ResponseBody   //返回的数据放到ResponseBody的数据区中
    public String baseType(int age){
        return "age:"+age;
    }

    //http://localhost:8080/baseType1.do?xage=10
    @RequestMapping(value="baseType1.do")
    @ResponseBody
    public String baseType1(@RequestParam(value="xage")int age){
        return "age: " + age;
    }

    /**
     *包装类型则可以接受空值，具有优势
     * 进行数据绑定的时候可以不传值
     * @param age
     * @return
     */
    //todo     http://localhost:8080/baseType2.do?age=10
    //todo     http://localhost:8080/baseType2.do
    //todo 400 http://localhost:8080/baseType2.do?age=abc
    @RequestMapping(value = "baseType2.do")
    @ResponseBody
    public String baseType2(Integer age){
        return "age:"+age;
    }

    //todo http://localhost:8080/array.do?name=Tom&name=Lucy&name=Jim
    @RequestMapping(value = "array.do")
    @ResponseBody
    public String array(String[] name){
        StringBuilder sbf = new StringBuilder();
        StringBuffer sb = new StringBuffer();
        for(String item : name){
            sbf.append(item).append(" ");
        }
        return sbf.toString();
    }

    //todo  http://localhost:8080/user?name=Tom&age=10
    //todo  http://localhost:8080/object.do?name=Tom&age=10&contactInfo.phone=10086
    @RequestMapping(value="/user")
    @ResponseBody
    public String GetUser(User user){
        return user.toString();
    }

    /**
     * 两个对象具有相同的属性，同属性进行数据绑定
     * @param user
     * @param admin
     * @return
     */
    //todo http://localhost:8080/object.do?name=Tom&age=10
    //TODO http://localhost:8080/object.do?name=Tom&age=10&contactInfo.phone=10086
    //TODO http://localhost:8080/object.do?user.name=Tom&admin.name=Lucy&age=10
    @RequestMapping(value = "object.do")
    @ResponseBody
    public String object(User user,Admin admin){
        return user.toString()+" "+admin.toString();
    }

    @InitBinder("user")  //@InitBinder 初始化一个对象
    public void initUser(WebDataBinder binder){
        binder.setFieldDefaultPrefix("user.");
    }
    @InitBinder("admin")
    public void initAdmin(WebDataBinder binder){
        binder.setFieldDefaultPrefix("admin.");
    }

    /**
     * List进行数据绑定
     * @param userListForm
     * @return
     */
    //TODO http://localhost:8080/list.do?users[0].name=Tom&users[1].name=Lucy
    //TODO http://localhost:8080/list.do?users[0].name=Tom&users[1].name=Lucy&users[20].name=Jim
    @RequestMapping(value = "list.do")
    @ResponseBody
    public String list(UserListForm userListForm){
        return "listSize:"+userListForm.getUsers().size() + "  " + userListForm.toString();
    }

    /**
     * list绑定数据
     * TODO http://localhost:8080/list.do?users[0].name=Tom&users[1].name=Lucy
     * @param userListForm
     * @return
     */
    @RequestMapping(value="list1.do")
    @ResponseBody
    public String list1(UserListForm userListForm){
        return userListForm.toString();
    }

    /**
     * set绑定数据
     * @param userSetForm
     * @return
     */
    //TODO http://localhost:8080/set.do?users[0].name=Tom&users[1].name=Lucy
    @RequestMapping(value = "set.do")
    @ResponseBody
    public String set(UserSetForm userSetForm){
        return userSetForm.toString();
    }

    /**
     * Map绑定数据
     * @param userMapForm
     * @return
     */
    //TODO http://localhost:8080/map.do?users['X'].name=Tom&users['X'].age=10&users['Y'].name=Lucy
    @RequestMapping(value = "map.do")
    @ResponseBody
    public String map(UserMapForm userMapForm){
        return userMapForm.toString();
    }


    /**
     * Json数据绑定
     * @param user
     * @return
     */
    /*
    {
        "name": "Jim",
            "age": 16,
            "contactInfo": {
                "address": "beijing",
                "phone": "10010"
              }
    }
    */
    //application/json
    @RequestMapping(value = "json.do")
    @ResponseBody
    public String json(@RequestBody User user){
        return user.toString();
    }


//    <?xml version="1.0" encoding="UTF-8" ?>
//    <admin>
//      <name>Jim</name>
//      <age>16</age>
//    </admin>
    //application/xml
    @RequestMapping(value = "xml.do")
    @ResponseBody
    public String xml(@RequestBody Admin admin){
        return admin.toString();
    }

    //TODO http://localhost:8080/date1.do/date1=2018-12-12
    @RequestMapping(value = "date1.do")
    @ResponseBody
    public String date1(Date date1){
        return date1.toString();
    }

    //TODO http://localhost:8080/date1.do/date1=2018-12-12
    @InitBinder("date1")
    public void initDate1(WebDataBinder binder){
        binder.registerCustomEditor(Date.class,new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),true));
    }

    //TODO http://localhost:8080/date2.do/date2=2018-12-12
    @RequestMapping(value = "date2.do")
    @ResponseBody
     public String date2(Date date2){
        return date2.toString();
    }


    //TODO http://localhost:8080/book
    @RequestMapping(value = "/book",method = RequestMethod.GET)
    @ResponseBody
    public String book(HttpServletRequest request){
        String contentType = request.getContentType();
        if(contentType == null){
            return "book.default";
        }else if(contentType.equals("txt")){
            return "book.txt";
        }else if(contentType.equals("html")){
            return "book.html";
        }
        return "book.default";
    }

    /**
     * GET 获取资源
     * @param subjectId
     * @return
     */
    @RequestMapping(value = "/subject/{subjectId}",method = RequestMethod.GET)
    @ResponseBody
    public String subjectGet(@PathVariable("subjectId") String subjectId){
        return "this is a get method,subjectId:"+subjectId;
    }

    /**
     * POST 创建资源
     * @param subjectId
     * @return
     */
    @RequestMapping(value = "/subject/{subjectId}",method = RequestMethod.POST)
    @ResponseBody
    public String subjectPost(@PathVariable("subjectId") String subjectId){
        return "this is a post method,subjectId:"+subjectId;
    }

    /**
     * DELETE 删除资源
     * @param subjectId
     * @return
     */
    @RequestMapping(value = "/subject/{subjectId}",method = RequestMethod.DELETE)
    @ResponseBody
    public String subjectDelete(@PathVariable("subjectId") String subjectId){
        return "this is a delete method,subjectId:"+subjectId;
    }

    /**
     * PUT创建（更新）资源
     * @param subjectId
     * @return
     */
    @RequestMapping(value = "/subject/{subjectId}",method = RequestMethod.PUT)
    @ResponseBody
    public String subjectPut(@PathVariable("subjectId") String subjectId){
        return "this is a put method,subjectId:"+subjectId;
    }


    /**
     * converter
     * @param bool
     * @return
     */
    @RequestMapping(value = "converter.do")
    @ResponseBody
    public String converter(Boolean bool){
        return bool.toString();
    }


}
