#!/bin/bash

echo "Building and running Village Store App..."
echo

echo "1. Building Android app..."
./gradlew :composeApp:assembleDebug
echo "Android app built successfully!"
echo

echo "2. Building and running Desktop app..."
./gradlew :composeApp:run
echo

echo "Setup complete! You can also run individual targets:"
echo "- Android: ./gradlew :composeApp:assembleDebug"
echo "- Desktop: ./gradlew :composeApp:run"
echo "- Web (WASM): ./gradlew :composeApp:wasmJsBrowserDevelopmentRun"
echo "- Web (JS): ./gradlew :composeApp:jsBrowserDevelopmentRun"