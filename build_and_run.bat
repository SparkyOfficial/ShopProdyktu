@echo off
echo Building and running Village Store App...
echo.

echo 1. Building Android app...
call .\gradlew.bat :composeApp:assembleDebug
echo Android app built successfully!
echo.

echo 2. Building and running Desktop app...
call .\gradlew.bat :composeApp:run
echo.

echo Setup complete! You can also run individual targets:
echo - Android: .\gradlew.bat :composeApp:assembleDebug
echo - Desktop: .\gradlew.bat :composeApp:run
echo - Web (WASM): .\gradlew.bat :composeApp:wasmJsBrowserDevelopmentRun
echo - Web (JS): .\gradlew.bat :composeApp:jsBrowserDevelopmentRun