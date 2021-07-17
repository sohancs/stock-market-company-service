FROM openjdk:8
ADD target/manage-company-service-0.0.1-SNAPSHOT.jar manage-company-service-0.0.1-SNAPSHOT.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","manage-company-service-0.0.1-SNAPSHOT.jar"]