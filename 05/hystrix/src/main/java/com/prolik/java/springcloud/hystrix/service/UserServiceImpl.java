package com.prolik.java.springcloud.hystrix.service;

import com.prolik.java.springcloud.hystrix.entity.User;
import com.prolik.java.springcloud.hystrix.service.impl.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class UserServiceImpl implements UserService{

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public User find(Long id) {
        return restTemplate.getForObject("http://USER-SERVICE/users/{1}"
                , User.class, id);
    }

    @Override
    public List<User> findAll(List<Long> ids) {
        return restTemplate.getForObject("http://USER-SERVICE/users?ids={1}"
                , List.class, StringUtils.join(ids, ","));
    }
}
