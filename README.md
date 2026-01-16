# JJKUR - Jujutsu Kaisen: Ultimate Reworked

[🇧🇷 Português](#português) | [🇺🇸 English](#english)

---

## Português

<p><strong style="font-size: 15px;">
    Mod completo para Minecraft Forge 1.20.1 que expande o mod Jujutsu Craft com novas mecânicas, habilidades e conteúdo.
  </strong></p>

## 🚀 Stack Tecnológica

<div style="display: flex; align-items: center; gap: 6px;">
  <h3 style="margin: 0;">Mod Development</h3>
  <img src="https://skillicons.dev/icons?i=java" height="30" alt="java logo" />
  <img src="https://skillicons.dev/icons?i=gradle" height="30" alt="gradle logo" />
</div>

- **Java 17** + Minecraft Forge 1.20.1
- **Forge Version**: 47.3.0
- **Build Tool**: Gradle 8.8
- **Localização**: `/src/main/java`

<div style="display: flex; align-items: center; gap: 6px;">
  <h3 style="margin: 0;">Dependências</h3>
</div>

- **Jujutsu Craft** (mod base, versão 42.2+)
- **GeckoLib** 4.4.9 (animações)
- **Player Animator** 1.0.2 (animações de jogador)
- **Mixin** 0.8.5 (modificações de código)

## 📦 Como executar

### 1. Pré-requisitos
```bash
# Java 17 instalado
java -version

# Gradle (incluído via wrapper)
./gradlew --version
```

### 2. Build do projeto
```bash
# Compilar o mod
./gradlew build

# O arquivo .jar será gerado em:
# build/libs/modid-1.0.jar
```

### 3. Executar ambiente de desenvolvimento
```bash
# Cliente Minecraft (8GB RAM alocados)
./gradlew runClient

# Servidor dedicado
./gradlew runServer

# Gerador de dados
./gradlew runData
```

### 4. Instalação manual
```bash
# 1. Copie o .jar gerado para a pasta mods do Minecraft
cp build/libs/*.jar ~/.minecraft/mods/

# 2. Certifique-se de ter as dependências instaladas:
# - Minecraft Forge 1.20.1-47.3.0
# - Jujutsu Craft 42.2+
# - GeckoLib
# - Player Animator
```

## 🎯 Funcionalidades
- ✅ Sistema de habilidades customizadas
- ✅ Novas entidades e mobs
- ✅ Blocos e itens exclusivos
- ✅ Encantamentos personalizados
- ✅ Efeitos de poção únicos
- ✅ Sistema de animações avançado
- ✅ Partículas e efeitos visuais
- ✅ Comandos customizados
- ✅ Integração completa com Jujutsu Craft
- ✅ Modelos 3D customizados (armas, armaduras)

## 📂 Estrutura do projeto
```
src/main/java/com/jujutsu/jujutsucraftaddon/
├── abilities/          # Habilidades customizadas
├── block/              # Blocos personalizados
├── client/             # Renderização client-side
├── command/            # Comandos do mod
├── configuration/      # Arquivos de configuração
├── enchantment/        # Encantamentos
├── entity/             # Entidades customizadas
├── fluid/              # Fluidos personalizados
├── init/               # Registro de componentes
├── item/               # Itens customizados
├── mixins/             # Modificações de código base
├── network/            # Sincronização cliente-servidor
├── potion/             # Efeitos de poção
├── procedures/         # Lógica de eventos
├── styles/             # Estilos de combate
├── utils/              # Utilitários
└── world/              # Geração de mundo
```

## 🔧 Configuração de desenvolvimento

### JVM Arguments (Cliente)
O projeto usa flags otimizadas do Aikar para melhor performance:
- **RAM**: 8GB (min/max)
- **GC**: G1GC com tuning avançado
- **Localização**: `build.gradle` → runs.client.jvmArgs

### Mixin Configuration
```json
// mixins.jujutsucraftaddon.json
- Debug verbose ativado
- Export de classes ativado
- Dump on failure ativado
```

## 🛠️ Comandos úteis
```bash
# Limpar build
./gradlew clean

# Compilar sem testes
./gradlew build -x test

# Gerar sources
./gradlew genSources

# Atualizar dependências
./gradlew --refresh-dependencies
```

## 🔄 CI/CD

### Workflows configurados
- **CI**: Build e testes automáticos em push/PR
- **PR Validation**: Validação obrigatória de Pull Requests
- **Release**: Geração automática de releases em tags
- **Dependabot**: Atualização automática de dependências

### Processo de PR
1. Crie uma branch a partir de `develop`
2. Faça suas alterações e commit
3. Abra um Pull Request para `develop`
4. Aguarde os checks automáticos passarem:
   - ✅ Build compilado com sucesso
   - ✅ Todos os testes passando
   - ✅ Validação do Gradle wrapper
5. Após aprovação, merge será liberado

### Proteção de branches
- `main`: Requer aprovação + testes passando
- `develop`: Requer testes passando

## 📝 Informações do mod
- **Mod ID**: jujutsucraftaddon
- **Nome**: JJKUR
- **Versão**: 9.0.43
- **Autor**: Satushi
- **Licença**: All rights reserved
- **CurseForge**: [Satuxhi Projects](https://www.curseforge.com/members/satuxhi/projects)

## 🎮 Modelos 3D incluídos
O projeto inclui diversos modelos customizados em `/models`:
- Armas (espadas, facas, armas de fogo)
- Armaduras e equipamentos
- Animações de combate
- Texturas personalizadas

---

## English

<p><strong style="font-size: 15px;">
    Complete addon for Minecraft Forge 1.20.1 that expands the Jujutsu Craft mod with new mechanics, abilities and content.
  </strong></p>

## 🚀 Tech Stack

<div style="display: flex; align-items: center; gap: 6px;">
  <h3 style="margin: 0;">Mod Development</h3>
  <img src="https://skillicons.dev/icons?i=java" height="30" alt="java logo" />
  <img src="https://skillicons.dev/icons?i=gradle" height="30" alt="gradle logo" />
</div>

- **Java 17** + Minecraft Forge 1.20.1
- **Forge Version**: 47.3.0
- **Build Tool**: Gradle 8.8
- **Location**: `/src/main/java`

<div style="display: flex; align-items: center; gap: 6px;">
  <h3 style="margin: 0;">Dependencies</h3>
</div>

- **Jujutsu Craft** (base mod, version 42.2+)
- **GeckoLib** 4.4.9 (animations)
- **Player Animator** 1.0.2 (player animations)
- **Mixin** 0.8.5 (code modifications)

## 📦 How to run

### 1. Prerequisites
```bash
# Java 17 installed
java -version

# Gradle (included via wrapper)
./gradlew --version
```

### 2. Build the project
```bash
# Compile the mod
./gradlew build

# The .jar file will be generated at:
# build/libs/modid-1.0.jar
```

### 3. Run development environment
```bash
# Minecraft Client (8GB RAM allocated)
./gradlew runClient

# Dedicated Server
./gradlew runServer

# Data Generator
./gradlew runData
```

### 4. Manual installation
```bash
# 1. Copy the generated .jar to Minecraft mods folder
cp build/libs/*.jar ~/.minecraft/mods/

# 2. Make sure you have the dependencies installed:
# - Minecraft Forge 1.20.1-47.3.0
# - Jujutsu Craft 42.2+
# - GeckoLib
# - Player Animator
```

## 🎯 Features
- ✅ Custom abilities system
- ✅ New entities and mobs
- ✅ Exclusive blocks and items
- ✅ Custom enchantments
- ✅ Unique potion effects
- ✅ Advanced animation system
- ✅ Particles and visual effects
- ✅ Custom commands
- ✅ Full integration with Jujutsu Craft
- ✅ Custom 3D models (weapons, armor)

## 📂 Project structure
```
src/main/java/com/jujutsu/jujutsucraftaddon/
├── abilities/          # Custom abilities
├── block/              # Custom blocks
├── client/             # Client-side rendering
├── command/            # Mod commands
├── configuration/      # Configuration files
├── enchantment/        # Enchantments
├── entity/             # Custom entities
├── fluid/              # Custom fluids
├── init/               # Component registration
├── item/               # Custom items
├── mixins/             # Base code modifications
├── network/            # Client-server sync
├── potion/             # Potion effects
├── procedures/         # Event logic
├── styles/             # Combat styles
├── utils/              # Utilities
└── world/              # World generation
```

## 🔧 Development configuration

### JVM Arguments (Client)
The project uses Aikar's optimized flags for better performance:
- **RAM**: 8GB (min/max)
- **GC**: G1GC with advanced tuning
- **Location**: `build.gradle` → runs.client.jvmArgs

### Mixin Configuration
```json
// mixins.jujutsucraftaddon.json
- Debug verbose enabled
- Class export enabled
- Dump on failure enabled
```

## 🛠️ Useful commands
```bash
# Clean build
./gradlew clean

# Compile without tests
./gradlew build -x test

# Generate sources
./gradlew genSources

# Update dependencies
./gradlew --refresh-dependencies
```

## 🔄 CI/CD

### Configured workflows
- **CI**: Automatic build and tests on push/PR
- **PR Validation**: Mandatory Pull Request validation
- **Release**: Automatic release generation on tags
- **Dependabot**: Automatic dependency updates

### PR Process
1. Create a branch from `develop`
2. Make your changes and commit
3. Open a Pull Request to `develop`
4. Wait for automatic checks to pass:
   - ✅ Build compiled successfully
   - ✅ All tests passing
   - ✅ Gradle wrapper validation
5. After approval, merge will be enabled

### Branch protection
- `main`: Requires approval + passing tests
- `develop`: Requires passing tests

## 📝 Mod information
- **Mod ID**: jujutsucraftaddon
- **Name**: JJKUR
- **Version**: 9.0.43
- **Author**: Satushi
- **License**: All rights reserved
- **CurseForge**: [Satuxhi Projects](https://www.curseforge.com/members/satuxhi/projects)

## 🎮 Included 3D models
The project includes several custom models in `/models`:
- Weapons (swords, knives, firearms)
- Armor and equipment
- Combat animations
- Custom textures
