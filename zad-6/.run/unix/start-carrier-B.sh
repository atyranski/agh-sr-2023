#!/bin/bash
# Please execute from the .run/unix folder: sh start-carrier-B.sh
echo "[INFO] Starting Carrier-B"
java -jar ../../space-carrier/target/space-carrier-1.0.0-jar-with-dependencies.jar \
      --name Carrier-B \
      --host localhost \
      --exchangeName space.topic \
      --qos 1 \
      --job-processor basic \
      --services passenger-transport cargo-transport
