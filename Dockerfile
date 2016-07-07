#
# Dockerfile to run clojure webapp "zap" and a local SQLITE database
#

FROM java:8

ADD target/zap-1.0.0-standalone.jar /tmp/zap-app.jar
ADD zap.db /tmp/zap.db

EXPOSE 8080
WORKDIR /tmp
CMD ["java", "-jar", "/tmp/zap-app.jar"]
