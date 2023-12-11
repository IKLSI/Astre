#!/bin/bash

# Set the path to the JavaFX SDK
FX_PATH="/Users/KLS/Downloads/FX/javafx-sdk-21.0.1/lib/"

# Compile Java files
javac --module-path "$FX_PATH" --add-modules javafx.graphics,javafx.controls,javafx.fxml,javafx.base,javafx.media,javafx.swing,javafx.web *.java

# Run the Java application
java --module-path "$FX_PATH" --add-modules javafx.graphics,javafx.controls,javafx.fxml,javafx.base,javafx.media,javafx.swing,javafx.web Essai

# Pause to keep the terminal open (optional)
read -p "Press Enter to exit"
