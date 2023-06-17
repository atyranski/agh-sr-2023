:: Please execute from the .run/windows folder: cmd.exe -/c ".\start-agency-A.bat"
echo [INFO] Starting Agency-A
java -jar ../../space-agency/target/space-agency-1.0.0-jar-with-dependencies.jar^
      --name Agency-A^
      --host localhost^
      --exchangeName space.topic^
      --job-provider-mode input