FROM gradle:jdk17 as builder
COPY --chown=gradle:gradle . /home/src
WORKDIR /home/src
RUN gradle bootJar

EXPOSE 9081:8080

ENTRYPOINT ["java","-jar","/home/src/build/libs/app.jar"]