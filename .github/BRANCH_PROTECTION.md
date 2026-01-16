# Configuração de Branch Protection
# Aplique estas regras no GitHub: Settings > Branches > Add rule

## IMPORTANTE: Ordem de configuração

### 1. Primeiro, faça um commit e push para criar os workflows:
```bash
git add .github/
git commit -m "ci: add CI/CD workflows"
git push origin develop
```

### 2. Crie um Pull Request de teste para ativar os workflows
- Isso fará os status checks aparecerem no GitHub
- Aguarde os workflows executarem pelo menos uma vez

### 3. Depois configure a proteção:

#### Para branch 'main':
1. Settings > Branches > Add rule
2. Branch name pattern: `main`
3. ✓ Require a pull request before merging
4. ✓ Require status checks to pass before merging
5. Clique em "Search for status checks" e selecione:
   - `build`
   - `validate`
6. ✓ Require branches to be up to date before merging
7. Save changes

#### Para branch 'develop':
1. Settings > Branches > Add rule
2. Branch name pattern: `develop`
3. ✓ Require status checks to pass before merging
4. Selecione: `build`
5. Save changes
