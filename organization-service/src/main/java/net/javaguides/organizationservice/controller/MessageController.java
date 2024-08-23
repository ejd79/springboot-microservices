package net.javaguides.organizationservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * I create this class in order to test to refresh config properties hosted on Github and served by
 * the Config Server, requesting the corresponding Client Actuator Refresh Endpoint
 */
@RefreshScope
@RestController
public class MessageController {

    @Value("${spring.boot.message}")
    private String message;

    @GetMapping("message")
    public String message(){
        return message;
    }
}
