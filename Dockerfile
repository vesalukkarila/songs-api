ARG JAVA_VARIANT=eclipse-temurin
ARG JAVA_VERSION=19

# BUILD STAGE
# ===========

# Use maven image for eclipse-temurin java v19
# https://hub.docker.com/_/maven/tags
FROM maven:3.9.0-${JAVA_VARIANT}-${JAVA_VERSION} AS build

# set working directory
WORKDIR /usr/src/app

# copy dependency manifest from local into the build container
COPY pom.xml .

# copy src files from local into the build container
COPY src/ src/

# install dependencies
RUN mvn clean install

# RUNTIME STAGE
# =============

# https://hub.docker.com/_/eclipse-temurin
FROM ${JAVA_VARIANT}:${JAVA_VERSION} AS runtime

# define variable for output path of jar file
ARG JAR_FILE=target/songsapi-1.0-SNAPSHOT.jar

# copy jar file from build stage
COPY --from=build /usr/src/app/${JAR_FILE} ${JAR_FILE}

# assign (docker) build variable as container (runtime) environment variable
ENV JAR_FILE=${JAR_FILE}

# run the application
# - shell used for environment variable expansion
# - run jar file with java
CMD ["sh", "-c", "java -jar ${JAR_FILE}"]