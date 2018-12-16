package com.imooc.common;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * @ClassName: MyDateFormatter
 * @Description:  Formatter 进行格式转换
 * @Author: liuhefei
 * @Date: 2018/12/15
 * @blog: https://www.imooc.com/u/1323320/articles
 **/
public class MyDateFormatter implements Formatter<Date> {

    /**
     * 将text转化为Date
     * @param text
     * @param locale
     * @return
     * @throws ParseException
     */
    public Date parse(String text, Locale locale) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(text);
    }

    public String print(Date object, Locale locale) {
        return null;
    }
}
