#!/bin/bash
set -e

echo "🧪 Testando CI/CD localmente..."
echo ""

echo "✅ Passo 1: Validando Gradle wrapper..."
if [ -f "gradlew" ]; then
    echo "   ✓ gradlew encontrado"
else
    echo "   ✗ gradlew não encontrado"
    exit 1
fi

echo ""
echo "✅ Passo 2: Compilando código..."
bash gradlew compileJava --no-daemon
echo "   ✓ Compilação bem-sucedida"

echo ""
echo "✅ Passo 3: Executando testes..."
bash gradlew test --no-daemon
echo "   ✓ Testes executados"

echo ""
echo "✅ Passo 4: Build completo..."
bash gradlew build --no-daemon
echo "   ✓ Build bem-sucedido"

echo ""
echo "🎉 Todos os checks passaram! Seu PR seria aprovado."
echo ""
echo "📦 Artefatos gerados em: build/libs/"
ls -lh build/libs/*.jar 2>/dev/null || echo "   (nenhum .jar encontrado)"
