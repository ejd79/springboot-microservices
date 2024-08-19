package net.javaguides.employee_service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * I create this class in order to test to refresh config properties hosted on Github and served by
 * the Config Server, requesting the corresponding Client Actuator Refresh Endpoint
 *
 * Another better option is to use RabbitMQ Message Broker. Just request:
 * http://localhost:8080/actuator/busrefresh
 * previously must be set:
 * 1. management.endpoints.web.exposure.include=* ,on service config file
 * 2. RebbitMQ must be configured in the Services:
 *          spring.rabbitmq.host=localhost
 *          spring.rabbitmq.port=5672
 *          spring.rabbitmq.username=guest
 *          spring.rabbitmq.password=guest
 *   ...and running, ex as Docker Container
 */
@RefreshScope
@RestController
public class MessageController {

    @Value("${spring.boot.message}")
    private String message;

    @GetMapping("/users/message")
    public String message(){
        return message;
    }
}
