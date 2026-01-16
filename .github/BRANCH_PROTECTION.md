# Configuração de Branch Protection
# Aplique estas regras no GitHub: Settings > Branches > Add rule

## IMPORTANTE: Ordem de configuração

### 1. Primeiro, faça um commit e push para criar os workflows:
```bash
git add .github/
git commit -m "ci: add CI/CD workflows"
git push origin master
```

### 2. Crie um Pull Request de teste para ativar os workflows:
```bash
git checkout -b test-ci
git push origin test-ci
# Abra um PR no GitHub: test-ci → master
```

### 3. Aguarde os workflows executarem
- Os status checks `build` e `validate` aparecerão automaticamente

### 4. Configure a proteção de branches:

#### Para branch 'master':
1. Settings > Branches > Add rule
2. Branch name pattern: `master`
3. ✓ Require a pull request before merging
4. ✓ Require status checks to pass before merging
5. Clique em "Search for status checks" e selecione:
   - `build`
   - `validate`
6. ✓ Require branches to be up to date before merging
7. Save changes

## Opcional: Criar branch develop
```bash
git checkout -b develop
git push origin develop
# Configure proteção similar para develop
```
