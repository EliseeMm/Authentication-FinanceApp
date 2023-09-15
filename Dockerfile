FROM ubuntu:20.04

RUN apt-get update && apt-get install -y openjdk-17-jdk

ENV JAVA_HOME=/usr/lib/jvm/default-jvm
ENV PATH="$PATH:$JAVA_HOME/bin"

WORKDIR /app

COPY . .

EXPOSE 5000
ADD target/Authentication-FinanceApp-1.0-SNAPSHOT.jar /app/server.jar
CMD ["java", "-jar", "server.jar"]