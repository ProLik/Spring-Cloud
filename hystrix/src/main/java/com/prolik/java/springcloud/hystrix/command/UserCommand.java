package com.prolik.java.springcloud.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.prolik.java.springcloud.hystrix.entity.User;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

public class UserCommand extends HystrixCommand<User>{

    private RestTemplate restTemplate;
    private Long id;

    public UserCommand(){
        //根据命令组来划分线程池
        /*
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GroupName"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("CommandName")));
        */
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CommandGroupKey"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("CommandKey"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("ThreadPoolKey")));
    }

    protected UserCommand(Setter setter, RestTemplate restTemplate, Long id) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("UserGroup")));
        this.id = id;
        this.restTemplate = restTemplate;
    }

    /**
     * 实现同步执行也可以实现异步执行
     * 同步执行：User user = new UserCommand(restTemplate, 1L).execute();
     * 异步执行：Future<User> userFuture = new UserCommand(restTemplate, 1L).queue();
     * @return
     * @throws Exception
     */
    @Override
    protected User run() throws Exception {
        return restTemplate.getForObject("http://USER-SERVICE/users{1}", User.class, id);
    }

    @Override
    protected User getFallback() {
        return new User();
    }

    /**
     * 开启缓存的ID
     * 请求缓存在run()和construct()执行之前生效，所以减少不必要的线程开销
     * @return
     */
    @Override
    protected String getCacheKey() {
        return String.valueOf(id);
    }
}
