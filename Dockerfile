FROM openjdk:17
COPY ./target/paymentms.jar /paymentms.jar
CMD ["java", "-jar", "/paymentms.jar"]