:: Please execute from the .run/windows folder: cmd.exe -/c ".\start-agency-B.bat"
echo [INFO] Starting Agency-B
java -jar ../../space-agency/target/space-agency-1.0.0-jar-with-dependencies.jar^
      --name Agency-B^
      --host localhost^
      --exchangeName space.topic^
      --job-provider-mode input