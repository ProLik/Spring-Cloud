package com.prolik.java.spring.helloservice.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {
    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private DiscoveryClient client;


    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(){
        ServiceInstance serviceInstance = client.getLocalServiceInstance();

        logger.info("/hello, host:" + serviceInstance.getHost() + ", service_id:" + serviceInstance.getServiceId());
        return "hello world";
    }

    @RequestMapping(value = "/hello1", method = RequestMethod.GET)
    public String hello(@RequestParam String name) {
        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info("/hello1, host:" + instance.getHost() + ", service_id:" + instance.getServiceId());
        return "Hello " + name;
    }

    @RequestMapping(value = "/hello2", method = RequestMethod.GET)
    public User hello(@RequestHeader String name, @RequestHeader Integer age) {
        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info("/hello2, host:" + instance.getHost() + ", service_id:" + instance.getServiceId());
        return new User(name, age);
    }

    @RequestMapping(value = "/hello3", method = RequestMethod.POST)
    public String hello(@RequestBody User user) {
        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info("/hello3, host:" + instance.getHost() + ", service_id:" + instance.getServiceId());
        return "Hello "+ user.getName() + ", " + user.getAge();
    }


}
