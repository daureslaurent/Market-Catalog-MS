FROM amazoncorretto:25-alpine

# Set working directory
WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ARG JAR_FILE=target/market-*.jar
COPY ${JAR_FILE} app.jar

# Expose default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-XX:+UseSerialGC","-XX:MaxRAMPercentage=60","-XX:InitialRAMPercentage=30","-XX:MaxMetaspaceSize=128m","-XX:CompressedClassSpaceSize=32m","-XX:MaxDirectMemorySize=64m","-jar","app.jar"]
