:: Please execute from the .run/windows folder: cmd.exe -/c ".\start-carrier-B.bat"
echo [INFO] Starting Carrier-B
java -jar ../../space-carrier/target/space-carrier-1.0.0-jar-with-dependencies.jar^
      --name Carrier-B^
      --host localhost^
      --exchangeName space.topic^
      --qos 1^
      --job-processor basic^
      --services passenger-transport cargo-transport