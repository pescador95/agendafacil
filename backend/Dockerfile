FROM maven:3.9.6 AS builder
WORKDIR /build

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine

WORKDIR /deployments

COPY --from=builder /build/target/quarkus-app/lib/ /deployments/lib/
COPY --from=builder /build/target/quarkus-app/*.jar /deployments/
COPY --from=builder /build/target/quarkus-app/app/ /deployments/app/
COPY --from=builder /build/target/quarkus-app/quarkus/ /deployments/quarkus/

ENV PORT="3000"
ENV HOST="0.0.0.0"
ENV JAVA_OPTS="-Dquarkus.http.host=$HOST -Dquarkus.http.port=$PORT -Djava.util.logging.manager=org.jboss.logmanager.LogManager"

ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

EXPOSE $PORT
CMD java $JAVA_OPTS -jar $JAVA_APP_JAR