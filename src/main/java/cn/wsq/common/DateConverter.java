package cn.wsq.common;

import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String s) {
        if(s==null||s.equals(""))return null;
        try{
            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            Date date=format.parse(s);
            return date;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
