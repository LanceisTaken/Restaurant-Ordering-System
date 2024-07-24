@echo off
setlocal
set APP_HOME=%~dp0
echo APP_HOME: %APP_HOME%
cd /d %APP_HOME%
echo Current Directory: %cd%

:: Path to the custom JDK image created with jlink
set JAVA_HOME=C:\Users\User\Documents\GitHub\Restaurant-Ordering-System\demo\src\main\resources\myapp-runtime
set JAR_FILE=C:\Users\User\Documents\GitHub\Restaurant-Ordering-System\out\artifacts\RestaurantThingy\demo.jar

:: Run the Java application with the custom JDK image
"%JAVA_HOME%\bin\java" --module-path "%JAVA_HOME%\jmods" --add-modules javafx.base,javafx.controls,javafx.fxml,javafx.graphics -cp "%JAR_FILE%" com.example.demo.LoginMenu

if %errorlevel% neq 0 (
    echo Java execution failed with error code %errorlevel%
) else (
    echo Java command executed successfully
)

pause
endlocal
