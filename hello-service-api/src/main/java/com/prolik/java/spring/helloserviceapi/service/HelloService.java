package com.prolik.java.spring.helloserviceapi.service;

import com.prolik.java.spring.helloserviceapi.entity.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/refactor")
public interface HelloService {
    @RequestMapping(value = "hello4", method = RequestMethod.GET)
    String hello(@RequestParam("name") String name);

    @RequestMapping(value = "hello5", method = RequestMethod.GET)
    String hello(@RequestParam("name") String name, @RequestParam("age") Integer age);

    @RequestMapping(value = "hello6", method = RequestMethod.POST)
    String hello(@RequestBody User usr);

}
