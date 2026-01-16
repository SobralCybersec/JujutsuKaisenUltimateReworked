# Configuração de Branch Protection
# Aplique estas regras no GitHub: Settings > Branches > Add rule

## Para branch 'main':
- Require pull request reviews before merging: ✓
- Require status checks to pass before merging: ✓
  - CI / build
  - PR Validation / validate
- Require branches to be up to date before merging: ✓
- Do not allow bypassing the above settings: ✓

## Para branch 'develop':
- Require status checks to pass before merging: ✓
  - CI / build
