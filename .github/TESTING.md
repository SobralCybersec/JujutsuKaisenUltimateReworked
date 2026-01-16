# 🧪 Guia de Testes CI/CD

## Testar localmente (antes de fazer push)

### Opção 1: Script automático
```bash
./test-ci.sh
```

### Opção 2: Comandos manuais
```bash
# 1. Compilar
bash gradlew compileJava --no-daemon

# 2. Executar testes
bash gradlew test --no-daemon

# 3. Build completo
bash gradlew build --no-daemon
```

## Testar no GitHub (fluxo completo)

### 1. Commit e push dos workflows
```bash
git add .github/ test-ci.sh
git commit -m "ci: add CI/CD workflows"
git push origin master
```

### 2. Criar branch de teste
```bash
git checkout -b test-ci-feature
echo "# Test" >> TEST.md
git add TEST.md
git commit -m "test: trigger CI"
git push origin test-ci-feature
```

### 3. Abrir Pull Request
1. Vá para: https://github.com/SobralCybersec/JujutsuKaisenUltimateReworked/pulls
2. Clique em "New Pull Request"
3. Selecione: `test-ci-feature` → `master`
4. Clique em "Create Pull Request"

### 4. Verificar workflows
- Vá na aba "Actions" do repositório
- Você verá os workflows executando:
  - ✅ **CI** - Build e testes
  - ✅ **PR Validation** - Validação completa

### 5. Ver resultado no PR
- O PR mostrará os checks:
  - ✅ `build` - passou
  - ✅ `validate` - passou
- Um comentário automático será adicionado

## Simular falha de teste

Para testar se o CI bloqueia PRs com falhas:

```bash
# Criar código com erro proposital
git checkout -b test-ci-fail
echo "public class Broken { invalid syntax }" > src/main/java/Broken.java
git add .
git commit -m "test: intentional failure"
git push origin test-ci-fail
```

Abra um PR e veja os checks falharem ❌

## Configurar proteção de branch

Após os workflows executarem pela primeira vez:

1. Settings > Branches > Add rule
2. Branch: `master`
3. ✓ Require status checks: `build`, `validate`
4. Save

Agora PRs só podem ser merged se os testes passarem! 🎉
