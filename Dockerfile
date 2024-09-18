FROM openjdk:21-jdk

WORKDIR /app

# Menyalin file .env ke dalam image
COPY .env /app

# Menyalin .jar file yang telah dibuild dari target directory
COPY target/springboot-data-penduduk-0.0.1-SNAPSHOT.jar /app/springboot-data-penduduk-0.0.1-SNAPSHOT.jar

# Menjalankan Spring Boot dengan opsi untuk membaca environment variables
ENTRYPOINT ["java", "-jar", "/app/springboot-data-penduduk-0.0.1-SNAPSHOT.jar"]
