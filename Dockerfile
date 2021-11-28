FROM gradle:jdk17 as builder
COPY --chown=gradle:gradle . /home/src
WORKDIR /home/src
RUN gradle build

FROM adoptopenjdk/openjdk:17-alpine as runner
COPY --from=builder /home/src/build/libs/*.jar /app.jar

EXPOSE 9081

ENTRYPOINT ["java","-jar","/app.jar"]