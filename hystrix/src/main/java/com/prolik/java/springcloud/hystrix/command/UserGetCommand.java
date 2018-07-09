package com.prolik.java.springcloud.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.prolik.java.springcloud.hystrix.entity.User;
import org.springframework.web.client.RestTemplate;

public class UserGetCommand extends HystrixCommand<User> {

    private static final HystrixCommandKey GETTER_KEY = HystrixCommandKey.Factory.asKey("GETTER_USER_KEY");
    private RestTemplate restTemplate;
    private Long id;

    protected UserGetCommand(RestTemplate restTemplate,  Long id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GETSETGET")).andCommandKey(GETTER_KEY));
        this.restTemplate = restTemplate;
        this.id = id;
    }


    @Override
    protected User run() throws Exception {
        return restTemplate.getForObject("http://USER-SERVICE/users/{1}", User.class, id);
    }

    @Override
    protected String getCacheKey() {
        return String.valueOf(id);
    }

    public static void flushCache(Long id){
        HystrixRequestCache.getInstance(GETTER_KEY, HystrixConcurrencyStrategyDefault.getInstance()).clear(String.valueOf(id));
    }
}
