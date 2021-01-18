FROM openjdk:15-alpine
VOLUME /tmp
WORKDIR /app
ADD /target/device-reg-service-1.0.jar /app/app.jar
RUN sh -c 'touch /app/app.jar'
EXPOSE 7070
ENV JAVA_OPTS=""
CMD [ "sh", "-c", "java $JAVA_OPTS -Xmx256m -Djava.security.egd=file:/dev/./urandom -jar /app/app.jar" ]