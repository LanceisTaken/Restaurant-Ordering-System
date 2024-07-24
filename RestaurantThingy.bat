@echo off
setlocal
set APP_HOME=%~dp0
echo APP_HOME: %APP_HOME%

:: Path to the custom JDK image created with jlink
set JAVA_HOME=%APP_HOME%demo\src\main\resources\myapp-runtime
set JAR_FILE=%APP_HOME%out\artifacts\RestaurantThingy\demo.jar

:: Change to the script's directory
cd /d %APP_HOME%

:: Run the Java application with the custom JDK image
"%JAVA_HOME%\bin\java" --module-path "%JAVA_HOME%\jmods" --add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics -cp "%JAR_FILE%" com.example.demo.LoginMenu

if %errorlevel% neq 0 (
    echo Java execution failed with error code %errorlevel%
) else (
    echo Java command executed successfully
)

pause
endlocal
