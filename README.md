# Data Penduduk

## Schema
![ERD](ERD_DATA_PENDUDUK.png)

## Postman Collection
Download here [Data Penduduk Collection](DATA-PENDUDUK-APP.postman_collection.json)

## Run Project
### Clean Package
```bash
API_PORT=8080 DB_HOST=localhost DB_PORT=5432 DB_NAME=data_penduduk_apps DB_USERNAME=jutioncandrakirana DB_PASSWORD=P@ssw0rd DDL_AUTO=update JWT_SECRET=R4ha5iiaa!!!! JWT_EXPIRATION=1 ADMIN_USERNAME=admin ADMIN_PASSWORD=password mvn clean package
```

### Run With JAR
```bash
API_PORT=8080 DB_HOST=localhost DB_PORT=5432 DB_NAME=data_penduduk_apps DB_USERNAME=jutioncandrakirana DB_PASSWORD=P@ssw0rd DDL_AUTO=update JWT_SECRET=R4ha5iiaa!!!! JWT_EXPIRATION=1 ADMIN_USERNAME=admin ADMIN_PASSWORD=password java -jar target/springboot-data-penduduk-0.0.1-SNAPSHOT.jar
```

**Note**:
- Change all environment configurations as needed.

### Run With Docker

```yaml
# version: '3.8' # Versi compose 2 tidak diperlukan lagi

services:
  app:
    build: .
    env_file:
      - .env
    ports:
      - "${API_PORT}:${API_PORT}"
    depends_on:
      db:
        condition: service_healthy  # Pastikan db sehat sebelum app dimulai

  db:
    image: postgres:alpine
    env_file:
      - .env
    environment:
      POSTGRES_USER: ${DB_USERNAME}
      POSTGRES_PASSWORD: ${DB_PASSWORD}
      POSTGRES_DB: ${DB_NAME}
    volumes:
      - datapenduduk-volume:/var/lib/postgresql/data
    healthcheck:
      test: ["CMD-SHELL", "pg_isready -U ${DB_USERNAME}"]
      interval: 10s
      timeout: 5s
      retries: 5

volumes:
  datapenduduk-volume:
    name: datapenduduk-volume
```

```
API_PORT=8080
DB_HOST=db
DB_PORT=5432
DB_NAME=jutionck
DB_USERNAME=jutionck
DB_PASSWORD=password
DDL_AUTO=update
JWT_SECRET=404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
JWT_EXPIRATION=36000
ADMIN_USERNAME=admin
ADMIN_PASSWORD=password
```

```bash
docker compose up
```