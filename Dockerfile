FROM gradle:8.2-jdk17 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle wrapper --no-daemon
RUN gradle build --no-daemon

# Stage 2: Run the application
FROM alpine
COPY --from=build /home/gradle/src/build/libs/*all.jar /opt/HarderMC.jar