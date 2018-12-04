package cn.wsq.config;

import cn.wsq.common.DateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
/*
* 日期转换器
* */
@Configuration
public class WebMvcConfig {
    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;
    @PostConstruct
    public void initEditableAvlidation(){
        ConfigurableWebBindingInitializer initializer=(ConfigurableWebBindingInitializer)handlerAdapter.getWebBindingInitializer();
        if(initializer.getConversionService()!=null){
            GenericConversionService genericConversionService= (GenericConversionService) initializer.getConversionService();
            genericConversionService.addConverter(new DateConverter());
        }
//        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer)handlerAdapter.getWebBindingInitializer();

    }
}
