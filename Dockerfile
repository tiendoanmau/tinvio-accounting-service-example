##############
# Dockerfile #
##############
FROM alpine:3.16 AS base

# Get the App Version
ARG APP_VERSION="0.0.1"

# Set the metadata
LABEL org.label-schema.name="Accounting-Service-Example" \
      org.label-schema.version=${APP_VERSION} \
      org.label-schema.vendor="Tinvio" \
      org.label-schema.schema-version="1.0"

# Install Deps
RUN set -ex && \
    apk -qU upgrade && \
    apk add -q openjdk11-jre tini

# Copy spring log files
COPY dockerfiles/spring.logrotate /etc/logrotate.d/spring
COPY dockerfiles/rotate.sh /etc/periodic/hourly

# Copy the entrypoint
COPY dockerfiles/entrypoint.sh /opt/app-entrypoint.sh

# Copy the jar file inside
COPY target/accounting-service-example-0.0.1-SNAPSHOT.jar /opt/app.jar

# Open tomcat port
EXPOSE 9099

# Set the default entrypoint
ENTRYPOINT ["/sbin/tini", "-g", "--", "/opt/app-entrypoint.sh"]