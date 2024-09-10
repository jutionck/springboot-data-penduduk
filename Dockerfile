FROM openjdk:21-jdk

WORKDIR /app

COPY target/springboot-data-penduduk-0.0.1-SNAPSHOT.jar /app/springboot-data-penduduk-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/app/springboot-data-penduduk-0.0.1-SNAPSHOT.jar"]