# springboot-microservices

# for running RabbitMQ Docker Container
docker run --rm -it -p 5672:5672 rabbitmq:3.13.6-alpine

# for running Zipkin Docker Container
docker run --rm -it --name zipkin -p 9411:9411 openzipkin/zipkin