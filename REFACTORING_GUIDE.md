# Refactoring Guide - JJKUR Mod

## Overview

This document outlines the refactoring approach for the JJKUR Minecraft mod, focusing on mixins, potions, and procedures packages.

## Principles Applied

### Clean Code
- Minimal code in mixins
- Clear, descriptive names
- Single responsibility per class
- No code duplication
- Extract complex logic to services

### SOLID Principles
- **Single Responsibility**: Each class has one clear purpose
- **Open/Closed**: Services are extensible without modification
- **Liskov Substitution**: Proper inheritance hierarchies
- **Interface Segregation**: Small, focused interfaces
- **Dependency Inversion**: Depend on abstractions

### Mixin Best Practices (SpongePowered)
- Use @Inject for non-invasive modifications
- Keep mixin methods minimal
- Extract business logic to service classes
- Proper use of @At injection points
- Avoid @Overwrite when possible

## Architecture

```
mixins/          - Minimal injection points only
services/        - Business logic and game mechanics
utils/           - Shared utility functions
procedures/      - Event handlers (to be refactored)
potion/          - Potion effects (to be refactored)
```

## Refactoring Pattern

### Before (Anti-pattern)
```java
@Mixin(SomeClass.class)
public class SomeMixin {
    @Inject(method = "someMethod", at = @At("HEAD"))
    private void onSomeMethod(CallbackInfo ci) {
        // 200+ lines of complex logic
        // Nested if statements
        // Duplicated code
        // Magic numbers
    }
}
```

### After (Clean Code)
```java
@Mixin(SomeClass.class)
public class SomeMixin {
    @Inject(method = "someMethod", at = @At("HEAD"))
    private void onSomeMethod(CallbackInfo ci) {
        SomeService.handleLogic((SomeClass)(Object)this);
    }
}

// In services/SomeService.java
public final class SomeService {
    private static final int CONSTANT = 10;
    
    private SomeService() {}
    
    public static void handleLogic(SomeClass instance) {
        // Clean, organized logic
        // Extracted methods
        // Named constants
    }
}
```

## Examples

### EntityMixin Refactoring

**Before**: 40 lines with nested conditionals and magic numbers
**After**: 15 lines delegating to EntityGlowService

**Benefits**:
- Testable service class
- Reusable glow logic
- Named constants for colors
- Clear separation of concerns

### PlayerAttackMixin Refactoring

**Before**: 400+ lines with massive nested if-else chains
**After**: 90 lines using switch statements and service delegation

**Benefits**:
- CombatStyleService handles unarmed combat
- WeaponCombatService handles weapon attacks
- Each style has its own method
- Eliminated code duplication
- Easy to add new styles/weapons

## Service Class Guidelines

### Structure
```java
public final class ServiceName {
    // Constants at top
    private static final int CONSTANT = 10;
    
    // Private constructor
    private ServiceName() {}
    
    // Public static methods
    public static void doSomething() {
        // Implementation
    }
    
    // Private helper methods
    private static void helperMethod() {
        // Implementation
    }
}
```

### Naming Conventions
- Service classes: `[Domain]Service.java`
- Methods: descriptive verbs (handle, process, calculate, etc.)
- Constants: UPPER_SNAKE_CASE
- Variables: camelCase

## Testing Strategy

### Unit Tests
- Test service classes independently
- Mock Minecraft dependencies
- Test edge cases
- Verify calculations

### Integration Tests
- Test mixin injection points
- Verify service integration
- Test game mechanics

## Migration Steps

1. Identify complex mixin
2. Create issue document
3. Extract logic to service class
4. Refactor mixin to delegate
5. Add tests
6. Update issue status
7. Commit with issue reference

## Commit Message Format

```
Refactor: [Component] description (Closes ISSUE_XXX)

- Extracted logic to [Service]
- Simplified [Mixin]
- Added constants for magic numbers
- Removed code duplication
```

## Progress Tracking

See `.issues/` folder for detailed tracking:
- ISSUE_001: Mixin refactoring (In Progress)
- ISSUE_002: Potion refactoring (Open)
- ISSUE_003: Procedures refactoring (Open)

## Next Steps

1. Continue refactoring remaining mixins
2. Create service classes for common patterns
3. Refactor potion package
4. Refactor procedures package
5. Add comprehensive tests
6. Update documentation

## Resources

- SpongePowered Mixin Docs: https://docs.spongepowered.org
- Clean Code Principles: Robert C. Martin
- SOLID Principles: Uncle Bob
- Minecraft Forge Docs: https://docs.minecraftforge.net
