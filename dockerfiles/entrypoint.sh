#!/usr/bin/env sh
##
# Accounting Service Entrypoint
##
set -ex

# Define log vars
SPRING_DIR=/var/log/spring
SPRING_LOG=${SPRING_DIR}/spring.log

# Create dir/files if not exists
[ ! -d ${SPRING_DIR} ] && mkdir ${SPRING_DIR}
[ ! -d ${SPRING_LOG} ] && touch ${SPRING_LOG}

# Start cron daemon for log rotation
/usr/sbin/crond

# Start the process
/usr/bin/java -Dspring.output.ansi.enabled=NEVER $@ -jar /opt/app.jar | tee -a ${SPRING_LOG}