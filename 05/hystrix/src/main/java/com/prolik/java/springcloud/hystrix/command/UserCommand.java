package com.prolik.java.springcloud.hystrix.command;

import com.netflix.hystrix.HystrixCommand;
import com.prolik.java.springcloud.hystrix.entity.User;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

public class UserCommand extends HystrixCommand<User>{

    private RestTemplate restTemplate;
    private Long id;

    protected UserCommand(Setter setter, RestTemplate restTemplate, Long id) {
        super(setter);
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
}
