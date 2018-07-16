package com.prolik.java.spring.streamhello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;


/**
 * @EnableBinding 该注解制定一个多个定义了@Input 或@Output注解接口，一次实现对消息通道（Channel）的绑定
 */
@EnableBinding(Sink.class)
public class SinReceiver {

    private static Logger logger = LoggerFactory.getLogger(SinReceiver.class);

    @StreamListener(Sink.INPUT)
    public void receive(Object payload){
        logger.info("Received:" + payload);
    }

}
