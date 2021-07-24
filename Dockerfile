FROM openjdk:8
ADD target/stock-market-company-service-1.0.0.jar stock-market-company-service-1.0.0.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","stock-market-company-service-1.0.0.jar"]