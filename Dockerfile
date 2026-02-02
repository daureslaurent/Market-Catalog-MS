FROM amazoncorretto:25-alpine

# Set working directory
WORKDIR /app

ARG JAR_FILE=target/market-*.jar
COPY ${JAR_FILE} app.jar

# Expose default Spring Boot port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","-jar","app.jar"]