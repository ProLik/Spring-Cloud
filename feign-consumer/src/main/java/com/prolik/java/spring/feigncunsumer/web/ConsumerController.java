package com.prolik.java.spring.feigncunsumer.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    @Autowired
    HelloService helloService;

    @RequestMapping(value = "/feign-consumer", method = RequestMethod.GET)
    public String helloConsumer(){
        return helloService.hello();
    }

    @RequestMapping(value = "/feign-consumer2", method = RequestMethod.GET)
    public String helloConsumer2(){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(helloService.hello("didi")).append(System.lineSeparator());
        stringBuffer.append(helloService.hello("meituan", 4)).append(System.lineSeparator());
        stringBuffer.append(helloService.hello(new User("li", 9))).append(System.lineSeparator());
        return stringBuffer.toString();
    }
}
