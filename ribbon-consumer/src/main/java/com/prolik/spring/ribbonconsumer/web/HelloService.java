package com.prolik.spring.ribbonconsumer.web;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;


@Service
public class HelloService {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "helloFallback", commandKey = "hellokey")
    public String hello(){
        long start = System.currentTimeMillis();
        StringBuilder result = new StringBuilder();

        result.append(restTemplate.getForEntity("http://HELLO-SERVICE/hello", String.class).getBody()).append("<br/>");
        result.append(restTemplate.getForEntity("http://HELLO-SERVICE/hello1?name={1}", String.class, "didi").getBody()).append("<br/>");

        HashMap<String, String> params = new HashMap<>();
        params.put("name", "didi");
        result.append(restTemplate.getForEntity("http://HELLO-SERVICE/hello1?name={name}", String.class, params)).append("<br/>");

        UriComponents uriComponents = UriComponentsBuilder.fromUriString("http://HELLO-SERVICE/hello1?name={name}").build().expand("dodo").encode();

        URI uri = uriComponents.toUri();

        result.append(restTemplate.getForEntity(uri, String.class).getBody()).append("<br/>");


        //POST
        User didi = new User("didi", 30);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://HELLO-SERVICE/hello3", didi, String.class);
        result.append(responseEntity.getBody()).append("<br/>");


        long end = System.currentTimeMillis();
        logger.info("Spend time:" + (end - start));
        return result.toString();

    }

    public String helloFallback(){
        return "error";
    }


}
