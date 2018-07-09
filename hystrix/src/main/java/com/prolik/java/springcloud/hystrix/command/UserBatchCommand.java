package com.prolik.java.springcloud.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.prolik.java.springcloud.hystrix.entity.User;
import com.prolik.java.springcloud.hystrix.service.impl.UserService;

import java.util.List;

public class UserBatchCommand extends HystrixCommand<List<User>> {

    UserService userService;
    List<Long> userIds;

    protected UserBatchCommand(UserService userService, List<Long> userIds) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("userServiceCommand")));
        this.userService = userService;
        this.userIds = userIds;
    }

    @Override
    protected List<User> run() throws Exception {
        return userService.findAll(userIds);
    }
}
