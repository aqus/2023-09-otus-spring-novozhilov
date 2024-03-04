FROM amazoncorretto:17.0.3-alpine as build
COPY . /tmp
WORKDIR /tmp
RUN ./mvnw clean package

# требуется, чтобы работал strip-debug
RUN apk add --no-cache binutils

# собираем маленький JRE-образ
RUN $JAVA_HOME/bin/jlink \
         --verbose \
         --add-modules ALL-MODULE-PATH \
         --strip-debug \
         --no-man-pages \
         --no-header-files \
         --compress=2 \
         --output /customjre

FROM alpine:latest
ENV JAVA_HOME=/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

# копируем JRE из базового образа
COPY --from=build /customjre $JAVA_HOME

ARG APPLICATION_USER=appuser
RUN adduser --no-create-home -u 1000 -D $APPLICATION_USER

RUN mkdir /app && \
    chown -R $APPLICATION_USER /app

USER 1000

COPY --from=build --chown=1000:1000 /tmp/target/hw17-docker-*.jar /app/app.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["/jre/bin/java", "-jar", "/app/app.jar"]

