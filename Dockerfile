FROM openjdk:21
COPY ./target/paymentms-0.0.1-SNAPSHOT.jar /paymentms.jar
CMD ["java", "-jar", "/paymentms.jar"]