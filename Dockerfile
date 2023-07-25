FROM ibm-semeru-runtimes:open-11-jre
COPY target/payment-service.jar payment-service.jar
ENTRYPOINT ["java","-jar","/payment-service.jar"]