#!/bin/bash

# Nome do arquivo ZIP
ZIP_NAME="JogoDaVida_Submission.zip"

# Criar diretório temporário para screenshots
SCREENSHOTS_DIR="screenshots"

echo "Por favor, coloque seus screenshots da Eclipse View - PIT Summary no diretório 'screenshots'"
echo "Pressione Enter quando estiver pronto..."
read

# Verificar se o diretório existe
if [ ! -d "$SCREENSHOTS_DIR" ]; then
    echo "Diretório 'screenshots' não encontrado. Criando diretório..."
    mkdir -p "$SCREENSHOTS_DIR"
    echo "Por favor, coloque seus screenshots no diretório 'screenshots' e execute este script novamente."
    exit 1
fi

# Verificar se há arquivos no diretório
if [ -z "$(ls -A $SCREENSHOTS_DIR)" ]; then
    echo "Nenhum screenshot encontrado no diretório 'screenshots'."
    echo "Por favor, adicione seus screenshots e execute este script novamente."
    exit 1
fi

# Adicionar screenshots ao ZIP
echo "Adicionando screenshots ao arquivo ZIP..."
zip -ur "$ZIP_NAME" "$SCREENSHOTS_DIR"/*

echo "Screenshots adicionados com sucesso ao arquivo $ZIP_NAME!" 