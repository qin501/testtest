package cn.wsq.common;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/*
* 根本反射去除string 中的空格
* */
public class TrimSpace {
    public static void  trim(Object obj){
        if(obj==null)return;
        //获得当前类的所有属性
        Field[] fields = obj.getClass().getDeclaredFields();
        for(Field field:fields){
            //属性名
            String name=field.getName();
            //将属性名第一个字母大写
            name=name.substring(0,1).toUpperCase()+name.substring(1);
            //属性的类型
            String type = field.getGenericType().getTypeName();
            //如果属性是string类型
            if(type.equals("java.lang.String")){
                try {
                    Method method = obj.getClass().getMethod("get" + name);
                    String value= (String) method.invoke(obj);//调用getter方法获取属性值
                    if(value!=null){
                        String trim = value.trim();
                        method = obj.getClass().getMethod("set" + name,String.class);
                        method.invoke(obj,trim);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
