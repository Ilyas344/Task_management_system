FROM amazoncorretto:17-alpine

# Update the package manager and install necessary tools
RUN apk update && \
    apk add --no-cache ca-certificates curl git openssh-client bash

# common for all images
LABEL org.opencontainers.image.title="Task Management System"
LABEL org.opencontainers.image.source="https://github.com/Ilyas344/Task_management_system"
LABEL org.opencontainers.image.url="https://github.com/Ilyas344/Task_management_system"


# Set environment variables for Maven
ENV MAVEN_HOME=/usr/share/maven
ENV MAVEN_CONFIG="/root/.m2"
ARG MAVEN_VERSION=3.9.9

# Copy Maven from the official Maven image
COPY --from=maven:3.9.9-eclipse-temurin-17 ${MAVEN_HOME} ${MAVEN_HOME}
COPY --from=maven:3.9.9-eclipse-temurin-17 /usr/local/bin/mvn-entrypoint.sh /usr/local/bin/mvn-entrypoint.sh
COPY --from=maven:3.9.9-eclipse-temurin-17 /usr/share/maven/ref/settings-docker.xml /usr/share/maven/ref/settings-docker.xml

# Create a symbolic link for Maven
RUN ln -s ${MAVEN_HOME}/bin/mvn /usr/bin/mvn

# Set working directory
WORKDIR /app

# Copy the project files and build the application
COPY pom.xml .
RUN mvn dependency:go-offline

COPY . .

# Build the Spring Boot application
RUN mvn clean package -DskipTests

# Expose the application port
EXPOSE 8080

# Set the entrypoint to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/target/TaskManagementSystem-0.0.1-SNAPSHOT.jar"]