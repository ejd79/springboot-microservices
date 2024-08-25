FROM eclipse-temurin:21
LABEL mantainer=erickjd79@gmail.com
WORKDIR /app
COPY target/service-registry-0.0.1-SNAPSHOT.jar /app/service-registry.jar
ENTRYPOINT ["java", "-jar", "service-registry.jar"]