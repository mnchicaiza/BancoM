FROM gradle:7.5.1-jdk17 AS build
WORKDIR /app
COPY .. .
RUN gradle clean build

FROM gcr.io/distroless/java17-debian12
MAINTAINER Magaly Chicaiza "noemi-2305@hotmail.com"
ENV TZ America/Guayaquil
ENV LANG C.UTF-8
EXPOSE 9001
WORKDIR /api
COPY --from=build /app/build/libs/clients-0.0.1-SNAPSHOT.jar /api/app.jar
CMD ["app.jar"]