#!/bin/bash
# Please execute from the .run/unix folder: sh start-agency-B.sh
echo "[INFO] Starting Agency-B"
java -jar ../../space-agency/target/space-agency-1.0.0-jar-with-dependencies.jar \
      --name Agency-B \
      --host localhost \
      --exchangeName space.topic \
      --job-provider input