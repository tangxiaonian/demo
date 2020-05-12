FROM openjdk:8-jdk-alpine
RUN echo "copy start..."
RUN mkdir /app
WORKDIR /app
COPY ./target/demo-0.0.1-SNAPSHOT.jar demo.jar
ENTRYPOINT ["java","-jar","demo.jar", "&"]