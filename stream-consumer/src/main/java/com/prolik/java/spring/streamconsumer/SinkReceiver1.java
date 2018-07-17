package com.prolik.java.spring.streamconsumer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(value = {Sink.class})
public class SinkReceiver1 {


    private static Logger logger = LoggerFactory.getLogger(StreamConsumerApplication.class);
}
