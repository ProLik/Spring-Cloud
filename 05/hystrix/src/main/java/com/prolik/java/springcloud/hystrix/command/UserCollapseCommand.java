package com.prolik.java.springcloud.hystrix.command;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;
import com.prolik.java.springcloud.hystrix.entity.User;
import com.prolik.java.springcloud.hystrix.service.impl.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserCollapseCommand extends HystrixCollapser<List<User>, User, Long> {

    private UserService userService;
    private Long userId;

    public UserCollapseCommand(UserService userService, Long userId) {
        super(Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("userCollapseCommand"))
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter().withTimerDelayInMilliseconds(100)));
        this.userService = userService;
        this.userId = userId;
    }

    @Override
    public Long getRequestArgument() {
        return userId;
    }

    /**
     *
     * @param collapsedRequests
     * @return
     */
    @Override
    protected HystrixCommand<List<User>> createCommand(Collection<CollapsedRequest<User, Long>> collapsedRequests) {
        ArrayList<Long> userIds = new ArrayList<>(collapsedRequests.size());
        userIds.addAll(collapsedRequests.stream().map(CollapsedRequest::getArgument).collect(Collectors.toList()));
        return new UserBatchCommand(userService, userIds);
    }

    /**
     * 该方法在批量执行 UserBatchCommand run 方法后，该方法执行，
     * batchResponse保存了createCommand 请求返回结果
     * collapsedRequests 代表了每个被合并的请求
     * @param batchResponse
     * @param collapsedRequests
     */
    @Override
    protected void mapResponseToRequests(List<User> batchResponse, Collection<CollapsedRequest<User, Long>> collapsedRequests) {
        int count = 0;
        for (CollapsedRequest<User, Long> collapsedRequest : collapsedRequests){
            User user = batchResponse.get(count++);
            collapsedRequest.setResponse(user);
        }
    }
}
