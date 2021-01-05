# Build and run the db

```sh
docker-compose -p app-micorservices-sample1 -f ./docker-compose-db.yml build
docker-compose -p app-micorservices-sample1 -f ./docker-compose-db.yml up -d
```

# Build and run the sample services

```sh
mvn clean package -Dmaven.test.skip=true
docker-compose -p app-micorservices-sample1 -f ./docker-compose.yml build
docker-compose -p app-micorservices-sample1 -f ./docker-compose.yml up -d
```

# P.S.

the frontend part is coming

