FROM gradle:jdk17 as builder
COPY --chown=gradle:gradle . /home/src
WORKDIR /home/src
RUN gradle build

EXPOSE 9081

ENTRYPOINT ["java","-jar","/home/src/build/libs/telegram-package-notifier-0.0.1-SNAPSHOT.jar"]