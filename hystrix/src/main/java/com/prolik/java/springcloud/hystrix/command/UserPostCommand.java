package com.prolik.java.springcloud.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.prolik.java.springcloud.hystrix.entity.User;
import org.springframework.web.client.RestTemplate;

public class UserPostCommand extends HystrixCommand<User> {

    private RestTemplate restTemplate;
    private User user;

    protected UserPostCommand(RestTemplate restTemplate, User user) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GETSETGET")));
        this.user = user;
        this.restTemplate = restTemplate;
    }

    @Override
    protected User run() throws Exception {
        //写操作
        User r = restTemplate.postForObject("http://USER-SERVICE/users", user, User.class);
        //刷新缓存，清理缓存中失效的User
        UserGetCommand.flushCache(user.getId());
        return r;
    }
}
