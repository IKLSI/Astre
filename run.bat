@echo off

REM Set the path to the JavaFX SDK
set FX_PATH="C:\chemin\vers\votre\FX\javafx-sdk-21.0.1\lib"

REM Compile Java files
javac --module-path %FX_PATH% --add-modules javafx.graphics,javafx.controls,javafx.fxml,javafx.base,javafx.media,javafx.swing,javafx.web *.java

REM Run the Java application
java --module-path %FX_PATH% --add-modules javafx.graphics,javafx.controls,javafx.fxml,javafx.base,javafx.media,javafx.swing,javafx.web Essai

REM Pause to keep the terminal open (optional)
set /p dummyVar=Press Enter to exit