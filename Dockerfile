
FROM openjdk:21-jdk

COPY /target/medical-clinic-0.0.1-SNAPSHOT.jar app/medical-clinic-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","app/medical-clinic-0.0.1-SNAPSHOT.jar"]