#!/bin/bash

# Compilar o projeto
mvn clean compile

# Executar a aplicação com o JavaFX
java --module-path /home/andrelcol/Downloads/openjfx-17.0.14_linux-x64_bin-sdk/lib \
     --add-modules javafx.controls,javafx.fxml \
     -cp target/classes \
     jogodavida.JogoDaVidaApp 