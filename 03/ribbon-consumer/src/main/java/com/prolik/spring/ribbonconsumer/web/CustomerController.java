package com.prolik.spring.ribbonconsumer.web;

import org.apache.coyote.http2.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
public class CustomerController {

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "/ribbon-consumer", method = RequestMethod.GET)
    public String helloCustomer(){

       /* UriComponents components = UriComponentsBuilder.fromUriString("http://USER-SERVICDE/user?name={name}").build().expand("dodo").encode();
        URI uri = components.toUri();
        restTemplate.getForEntity(uri, String.class);*/
        return  restTemplate.getForEntity("http://HELLO-SERVICR/hello", String.class).getBody();
    }
}
