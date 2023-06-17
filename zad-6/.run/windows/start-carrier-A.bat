:: Please execute from the .run/windows folder: cmd.exe -/c ".\start-carrier-A.bat"
echo [INFO] Starting Carrier-A
java -jar ../../space-carrier/target/space-carrier-1.0.0-jar-with-dependencies.jar \
      --name Carrier-A^
      --host localhost^
      --exchangeName space.topic^
      --qos 1^
      --job-processor basic^
      --services passenger-transport place-satellite-in-orbit