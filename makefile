.PHONY: all clean build deploy

all: deploy

# Variable
APP_NAME := springboot-data-penduduk-0.0.1-SNAPSHOT
TARGET_FOLDER := target
JAR_FILE := $(TARGET_FOLDER)/$(APP_NAME).jar
API_PORT := 8080
DB_HOST := localhost
DB_PORT := 5432
DB_NAME := data_penduduk_apps
DB_USERNAME := jutioncandrakirana
DB_PASSWORD := P@ssw0rd
DDL_AUTO := update
JWT_SECRET := 404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970
JWT_EXPIRATION := 36000
ADMIN_USERNAME := admin
ADMIN_PASSWORD := password

clean:
	@echo "Remove existing app $(APP_NAME)..."
	@rm -rf $(TARGET_FOLDER)

build: clean
	@echo "Building Spring Boot app with Maven..."
	@API_PORT=$(API_PORT) DB_HOST=$(DB_HOST) DB_PORT=$(DB_PORT) DB_NAME=$(DB_NAME) DB_USERNAME=$(DB_USERNAME) DB_PASSWORD=$(DB_PASSWORD) DDL_AUTO=${DDL_AUTO} JWT_SECRET=${JWT_SECRET} JWT_EXPIRATION=${JWT_EXPIRATION} ADMIN_USERNAME=${ADMIN_USERNAME} ADMIN_PASSWORD=${ADMIN_PASSWORD} mvn clean package -DskipTests

deploy: build
	@echo "Running new app $(APP_NAME)..."
	@API_PORT=$(API_PORT) DB_HOST=$(DB_HOST) DB_PORT=$(DB_PORT) DB_NAME=$(DB_NAME) DB_USERNAME=$(DB_USERNAME) DB_PASSWORD=$(DB_PASSWORD) DDL_AUTO=${DDL_AUTO} JWT_SECRET=${JWT_SECRET} JWT_EXPIRATION=${JWT_EXPIRATION} ADMIN_USERNAME=${ADMIN_USERNAME} ADMIN_PASSWORD=${ADMIN_PASSWORD} java -jar $(JAR_FILE)
	@echo "App $(APP_NAME) is now running"