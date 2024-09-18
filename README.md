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

## Kubernetes
Server Kubernetes
```bash
kubectl --kubeconfig=/<pathtodirectory>/k8s-1-31-1-do-0-sgp1-1726628074294-kubeconfig.yaml get nodes
```

Apply Kubernetes:
```bash
# Terapkan ConfigMap untuk konfigurasi aplikasi
kubectl --kubeconfig=/Users/jutioncandrakirana/.kube/config.yaml apply -f k8s/app-config.yaml

# Terapkan Secret untuk menyimpan credential sensitif seperti password
kubectl --kubeconfig=/Users/jutioncandrakirana/.kube/config.yaml apply -f k8s/app-secrets.yaml

# Terapkan PersistentVolumeClaim (PVC) untuk PostgreSQL
kubectl --kubeconfig=/Users/jutioncandrakirana/.kube/config.yaml apply -f k8s/postgres-pvc.yaml

# Terapkan Deployment untuk PostgreSQL (Database)
kubectl --kubeconfig=/Users/jutioncandrakirana/.kube/config.yaml apply -f k8s/db-deployment.yaml

# Terapkan Service untuk PostgreSQL (Database)
kubectl --kubeconfig=/Users/jutioncandrakirana/.kube/config.yaml apply -f k8s/db-service.yaml

# Terapkan Deployment untuk Aplikasi
kubectl --kubeconfig=/Users/jutioncandrakirana/.kube/config.yaml apply -f k8s/app-deployment.yaml

# Terapkan Service untuk Aplikasi
kubectl --kubeconfig=/Users/jutioncandrakirana/.kube/config.yaml apply -f k8s/app-service.yaml
```

Penjelasan Singkat:
- `app-config.yaml`: File ConfigMap untuk mengatur variabel konfigurasi aplikasi.
- `app-secrets.yaml`: File Secret untuk menyimpan informasi sensitif seperti password database.
- `postgres-pvc.yaml`: File PersistentVolumeClaim (PVC) untuk menyimpan data PostgreSQL secara persisten.
- `db-deployment.yaml`: File Deployment untuk PostgreSQL (database).
- `db-service.yaml`: File Service untuk mengekspos PostgreSQL dalam cluster Kubernetes.
- `app-deployment.yaml`: File Deployment untuk aplikasi Anda.
- `app-service.yaml`: File Service untuk mengekspos aplikasi Anda, sehingga dapat diakses dari luar melalui NodePort.

- Setelah menjalankan semua perintah ini, Anda dapat memverifikasi status dari resource yang diterapkan dengan perintah berikut:

```bash
kubectl --kubeconfig=/Users/jutioncandrakirana/.kube/config.yaml get pods
kubectl --kubeconfig=/Users/jutioncandrakirana/.kube/config.yaml get svc
```
Berikutnya untuk akses atau uji coba menggunakan postman:

### Cara 1
Cara 1 kita lakukan `port-forwading`:
```bash
kubectl --kubeconfig=/Users/jutioncandrakirana/.kube/config.yaml port-forward svc/app-service 8080:8080
```
Setelah itu akses menggunakan `postman` menggunakan url `localhost:8080`.

### Cara 2
Cara 2 kita akses langsung `IP NodePort` dan `Port NodePort` nya. Cara cek nya seperti berikut:
```bash
kubectl --kubeconfig=/Users/jutioncandrakirana/.kube/config.yaml get nodes -o wide
```

Setelah itu akses menggunakan `postman` menggunakan url `http://167.71.192.246:32090`.
