package com.prolik.java.springcloud.hystrix.command;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.prolik.java.springcloud.hystrix.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

/**
 * HystrixCommand 返回的Observable 只能发射一次数据
 * HystrixObservableCommand,可以发送多次数据
 */

public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 通过注解优雅的实现Hystrix命令定义
     * 同步的实现方式
     *
     * @param id
     * @return
     */
    @HystrixCommand(/*fallbackMethod = "getDefaultUser"*/ fallbackMethod = "getRemoteUser")
    public User getUserById(Long id) {
        return restTemplate.getForObject("http://USER-SERVICE/users/{1}", User.class, id);
    }

    /**
     * 通过异步的方式来实现
     *
     * @param id
     * @return
     */
    @HystrixCommand
    public Future<User> getUserByIdAsync(final String id){
        return new AsyncResult<User>() {
            @Override
            public User invoke() {
                return restTemplate.getForObject("http://USER-SERVICE/users/{1}", User.class, id);
            }
        };
    }

    /**
     * getDefaultUser方法必须与HystrixCommand在同一个类中
     * public private 均可以
     * @return
     */
    public User getDefaultUser(){

        return new User();
    }

    @HystrixCommand(fallbackMethod = "getDefaultUser",ignoreExceptions = {HystrixBadRequestException.class})
    public User getRemoteUser(){
        /*exits error*/
        return new User();
    }
}
