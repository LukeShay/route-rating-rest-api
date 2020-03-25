FROM lambci/lambda:build-java11

RUN mkdir -p /app
ENV PROJECT_HOME /app

WORKDIR $PROJECT_HOME

COPY gradle ./gradle
COPY gradlew ./gradlew

RUN ./gradlew --build-cache --no-daemon

COPY src ./src
COPY build.gradle ./build.gradle
COPY gradle.properties ./gradle.properties
COPY settings.gradle ./settings.gradle

CMD ./gradlew -x test \
        -x distTar \
        -x distZip \
        -x bootDistTar \
        -x bootDistZip \
        -x bootJar \
        -x startScripts \
        -x bootStartScripts \
        -x verifyGoogleJavaFormat \
        build
