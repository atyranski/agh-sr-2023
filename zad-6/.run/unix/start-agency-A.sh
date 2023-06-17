#!/bin/bash
# Please execute from the .run/unix folder: sh start-agency-A.sh
echo "[INFO] Starting Agency-A"
java -jar ../../space-agency/target/space-agency-1.0.0-jar-with-dependencies.jar \
      --name Agency-A \
      --host localhost \
      --exchangeName space.topic \
      --job-provider-mode input