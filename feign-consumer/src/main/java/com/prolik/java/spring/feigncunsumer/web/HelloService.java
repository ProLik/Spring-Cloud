package com.prolik.java.spring.feigncunsumer.web;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient("hello-service")
public interface HelloService {
    @RequestMapping(value = "/hello")
    String hello();


    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    String hello(@RequestParam(value = "name") String name);

    @RequestMapping(value = "/hello2", method = RequestMethod.GET)
    String hello(@RequestHeader(value = "name") String name, @RequestHeader(value = "age") Integer age);

    @RequestMapping(value = "/hello3", method = RequestMethod.POST)
    String hello(@RequestBody User user);



}
