package io.pivotal.helloui.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Configuration
public class SayHelloCommand {

    @Autowired
    RestTemplate restTemplate;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Do any additional configuration here
        return builder.build();
    }

    @HystrixCommand(fallbackMethod = "sayHelloFallback")
    public String sayHello(String toWho) {
        try{
            return restTemplate.getForObject("https://hello-world-server/hello?name={name}", String.class, toWho);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public String sayHelloFallback(String toWho) {
        return String.format("Error, can't say hello to %s", toWho);
    }

}
