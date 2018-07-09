package com.prolik.java.springcloud.hystrix.command;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
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
    @CacheResult(cacheKeyMethod = "getUserByIdCacheKey")
    //设置请求缓存 默认key为所有参数,cacheKeyMethod 优先级比@CacheKey高
    @HystrixCommand(/*fallbackMethod = "getDefaultUser"*/ fallbackMethod = "getRemoteUser")
    public User getUserById(@CacheKey("id") Long id) {
        //CacheKey 可以指定对象中的属性 如：@CacheKey("id") User user
        return restTemplate.getForObject("http://USER-SERVICE/users/{1}", User.class, id);
    }

    /**
     * 通过异步的方式来实现
     *
     * @param id
     * @return
     */
    @HystrixCommand(commandKey = "getUserById", groupKey = "UserGroup", threadPoolKey = "getUserByIdThread")
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
    public User getDefaultUser(String id, Throwable e){
        if(true){
            boolean error = e.getMessage().equals("error");
        }
        return new User();
    }

    @HystrixCommand(fallbackMethod = "getDefaultUser",ignoreExceptions = {HystrixBadRequestException.class})
    public User getRemoteUser(String id){
        if(true){
            throw new RuntimeException("error");
        }
        /*exits error*/
        return new User();
    }

    private Long getUserByIdCacheKey(Long id){
        return id;
    }

    //注：commandKey必须指定对应的请求缓存
    @CacheRemove(commandKey = "getUserById")
    @HystrixCommand
    public User update(@CacheKey("id") User user){
        return restTemplate.postForObject("http://USER-SERVICE/users", user, User.class);
    }
}
