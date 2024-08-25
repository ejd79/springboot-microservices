# for running RabbitMQ Docker Container
docker run --rm -it -p 5672:5672 rabbitmq:3.13.6-alpine

# for running Zipkin Docker Container
docker run --rm -it --name zipkin --network springboot-microservices-net -p 9411:9411 openzipkin/zipkin

# TODO 
### Check Tracing Code: 
https://github.com/RameshMF/springboot-microservices/tree/main/v3/springboot-microservices

# Circuit Breaker Pattern implementation
## Add the Spring Cloud 2 Starter of Resilience4j to your compile dependency.
The Spring Cloud 2 Starter allows you to use Spring Cloud Config as a central place to manage and refresh properties 
at runtime.


#TIPS
## Convert Yaml to .properties files
https://mageddo.com/tools/yaml-converter

# OpenAPI (Swagger)

## http://localhost:8080/swagger-ui/index.html

http://localhost:8080/v3/api-docs  : genera doc en formato json  

Desde la interfaz de Swagger puedes probar los servicios

Use **@OpenAPIDefinition** 


