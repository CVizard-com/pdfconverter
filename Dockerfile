FROM maven:3-amazoncorretto-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests
RUN mvn wrapper:wrapper

FROM amazoncorretto:17
WORKDIR /app
COPY --from=build /app/target/pdfconverter.jar .
EXPOSE 8083
<<<<<<< HEAD
CMD ["java", "-jar", "pdfconverter.jar"]
=======
CMD ["java", "-jar", "pdfconverter.jar"]
>>>>>>> 9f4a598706eeceac01c093e455f4f6384e9a4591
