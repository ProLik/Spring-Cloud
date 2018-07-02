package com.prolik.java.springcloud.hystrix.command;

import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.prolik.java.springcloud.hystrix.entity.User;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

public class UserObservableCommand extends HystrixObservableCommand<User> {

    private RestTemplate restTemplate;
    private Long id;

    protected UserObservableCommand(Setter setter, RestTemplate restTemplate, Long id) {
        super(setter);
        this.restTemplate = restTemplate;
        this.id = id;
    }

    /**
     * 需要将命令的执行逻辑放在construct方法中重载
     * @return
     */
    @Override
    protected Observable<User> construct() {
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                try{
                    if(!subscriber.isUnsubscribed()){
                        User user = restTemplate.getForObject("http://USER-SERVICE/users/{1}", User.class, id);
                        subscriber.onNext(user);
                        subscriber.onCompleted();
                    }
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }

    /**
     * EAGER 表示使用observe()方式，立即执行 该命令会在observe（）被调用时立即执行
     * LAZY 表示使用toObserve()方式，当所有订阅都订阅它之后才会执行
     * @param id
     * @return
     */
    @HystrixCommand(observableExecutionMode = ObservableExecutionMode.EAGER)
    public Observable<User> getUserById(final String id){
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                try{
                    if(!subscriber.isUnsubscribed()){
                        User user = restTemplate.getForObject("http://USER-SERVICE/users/{1}", User.class, id);
                        subscriber.onNext(user);
                        subscriber.onCompleted();
                    }
                }catch (Exception e){
                    subscriber.onError(e);
                }
            }
        });
    }
}
