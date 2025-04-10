FROM bellsoft/liberica-openjdk-alpine:17
CMD ["./gradlew", "clean", "build"]

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} sponjy-app-backend.jar

ARG PROFILE=test
ENV PROFILE=${PROFILE}

ENTRYPOINT ["java", "-jar", "/sponjy-app-backend.jar","--spring.profiles.active=${PROFILE}"]
