package com.prolik.java.springcloud.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.prolik.java.springcloud.hystrix.entity.User;
import org.springframework.web.client.RestTemplate;

public class UserGetCommand extends HystrixCommand<User> {

    private static final HystrixCommandKey GETTER_KEY = HystrixCommandKey.Factory.asKey("GETTER_USER_KEY");
    private RestTemplate restTemplate;
    private Long id;

    protected UserGetCommand(RestTemplate restTemplate,  Long id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GETSETGET")));
        this.restTemplate = restTemplate;
        this.id = id;
    }


    @Override
    protected User run() throws Exception {
        return null;
    }
}
