package com.prolik.spring.Hello.config;

import com.netflix.loadbalancer.ILoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.HashMap;
import java.util.Map;

public class PropertiesFactory {

    @Autowired
    private Environment environment;


    private Map<Class, String> classToProperty = new HashMap<>();


    public PropertiesFactory(){
        classToProperty.put(ILoadBalancer.class, "NF");

    }
}
