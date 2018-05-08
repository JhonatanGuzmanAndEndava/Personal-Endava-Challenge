# Personal-Endava-Challenge

Welcome to Personal Endava Challenge, Readers Nest Project has:

* 3 Microservices using Spring Boot (Users, Books, Bookclubs) 
* 2 Databases MongoDB and MySQL
* Graphql Server for process microservices REST API
* Jenkinsfile for CI/CD and deploy to AWS
* docker-compose file to deploy in localhost


## Prerrequisites
* JDK 1.8
* Maven
* Docker

## Run in localhost

1. Enter to each microservice and run 

```
cd readers-nest-backend/bookclubs
./mvnw clean package
```

If you have maven in path variable you can execute outside the folder

```
readers-nest-backend/bookclubs/mvn clean package
```

Those commands will create a target folder with .jar file that contains all code ready for deploy in docker container

2. Run the command

```
docker-compose up -d
```

3. Test the complete project through localhost:3000

4. Enjoy

## Warning

Token Auth just has 5 minutes for work, If you spend more time, you have to do a login again in the front end in order to refresh token.

## Notes

Microservices were made with IntelliJ and Spring Boot Framework 
