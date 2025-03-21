#!/bin/bash

# Nome do arquivo ZIP
ZIP_NAME="JogoDaVida_Submission.zip"

# Criar diretório temporário
TEMP_DIR="submission_temp"
mkdir -p "$TEMP_DIR"

# Copiar arquivos do projeto
echo "Copiando arquivos do projeto..."
cp -r src "$TEMP_DIR/"
cp -r .settings "$TEMP_DIR/"
cp .project "$TEMP_DIR/"
cp .classpath "$TEMP_DIR/"
cp pom.xml "$TEMP_DIR/"

# Copiar relatórios
echo "Copiando relatórios..."
cp relatorio_parte1.txt "$TEMP_DIR/"
cp relatorio_parte2.txt "$TEMP_DIR/"
cp relatorio_parte3.txt "$TEMP_DIR/"

# Copiar relatórios de cobertura
echo "Copiando relatórios de cobertura..."
mkdir -p "$TEMP_DIR/reports"
cp -r target/pit-reports "$TEMP_DIR/reports/"
cp target/jacoco.exec "$TEMP_DIR/reports/"
cp -r target/surefire-reports "$TEMP_DIR/reports/"

# Criar arquivo ZIP
echo "Criando arquivo ZIP..."
cd "$TEMP_DIR"
zip -r "../$ZIP_NAME" ./*
cd ..

# Limpar diretório temporário
echo "Limpando arquivos temporários..."
rm -rf "$TEMP_DIR"

echo "Arquivo $ZIP_NAME criado com sucesso!"
echo "Por favor, não se esqueça de adicionar os screenshots da Eclipse View - PIT Summary ao arquivo ZIP." 