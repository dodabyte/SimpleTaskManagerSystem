FROM openjdk:17
WORKDIR /app
COPY target/SimpleTaskManagerSystem-0.0.1-SNAPSHOT.jar .
EXPOSE 9001
CMD ["java", "-jar", "SimpleTaskManagerSystem-0.0.1-SNAPSHOT.jar"]