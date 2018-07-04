package com.prolik.java.springcloud.hystrix.service.impl;

import com.prolik.java.springcloud.hystrix.entity.User;

import java.util.List;

public interface UserService {

    User find(Long id);

    List<User> findAll(List<Long> ids);
}
