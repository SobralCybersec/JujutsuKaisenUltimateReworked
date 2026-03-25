# Clean Code & SOLID Guidelines for Java Minecraft Forge Mods (AI Coding Rule)

Version: 2.0  
Date: 2025-01-XX  
Author: Matheus

---

# 1. Purpose

This rule defines coding standards to ensure that generated Java code for Minecraft Forge mods is:

- readable
- maintainable
- modular
- aligned with SOLID principles
- aligned with Clean Code practices
- compliant with Mixin best practices (SpongePowered)

The goal is to avoid common patterns produced by AI that generate verbose, redundant or poorly structured code.

---

# 2. General Guidelines

The AI MUST follow these principles when generating Java code.

| # | Rule | Reason |
|---|------|-------|
| 1 | Never use emojis in comments, logs, or documentation | Keeps code professional |
| 2 | Avoid redundant comments | Only explain complex logic |
| 3 | Avoid unnecessary System.out.println | Do not pollute the console |
| 4 | Use clear and meaningful variable names | Improves readability |
| 5 | Follow SOLID principles strictly | Improves maintainability |
| 6 | Each method must have a single responsibility | Reduces complexity |
| 7 | Keep code concise and readable | Avoid verbose patterns |
| 8 | Avoid redundant loops or unnecessary logic | Improves performance |
| 9 | Prefer standard Java libraries | Avoid unnecessary custom implementations |
| 10 | Optimize code like a human developer would | AI tends to generate generic solutions |
| 11 | If the AI is uncertain about an implementation, research the correct approach before generating code | Prevents hallucinated solutions |
| 12 | Avoid typical AI documentation style | Follow the documentation style defined here: https://raw.githubusercontent.com/SobralCybersec/BungouStrayERP/refs/heads/main/README.md |

---

# 3. Control Flow Rules

The AI must follow these control flow practices.

## Prefer IF statements

Use `if` for simple conditions.

## Prefer SWITCH instead of long ELSE chains

Bad example:


if (status.equals("A")) {
...
} else if (status.equals("B")) {
...
} else if (status.equals("C")) {
...
}


Preferred:

```java
switch (status) {
case "A":
...
break;
case "B":
...
break;
case "C":
...
break;
}
```


## Avoid unnecessary ELSE blocks

Preferred pattern:

```java
if (condicaoInvalida) {
return;
}

processar();
```


This reduces nesting and improves readability.

---

# 4. Exception Handling Rules

Avoid excessive try/catch usage.

Guidelines:

1. Do not wrap entire methods in try/catch.
2. Handle exceptions only when meaningful recovery exists.
3. Prefer throwing exceptions to higher layers.
4. Use custom exceptions for domain errors.

Bad example:

```java
try {
executarOperacao();
} catch (Exception e) {
e.printStackTrace();
}
```

Preferred:

executarOperacao();

or

throw new IllegalStateException("Invalid operation");

Exception handling should be centralized when possible.

---

# 5. SOLID Principles (Mandatory)

The generated code must follow SOLID principles.

## Single Responsibility Principle

Each class must have one responsibility.

Bad:

```java 
    class PedidoService {
    void salvarPedido() {}
    void enviarEmail() {}
    }
```

Good:

```java
class PedidoService {
void salvarPedido() {}
}

class NotificacaoService {
void enviarEmail() {}
}
```

---

## Open/Closed Principle

Classes must be open for extension but closed for modification.

Prefer interfaces and polymorphism.

---

## Liskov Substitution Principle

Subclasses must behave correctly when replacing their parent class.

---

## Interface Segregation Principle

Prefer multiple small interfaces instead of large ones.

Bad:

```java
interface Sistema {
void salvar();
void deletar();
void enviarEmail();
}
```

Good:

```java
interface Persistencia {
void salvar();
void deletar();
}

interface Notificacao {
void enviarEmail();
}
```

---

## Dependency Inversion Principle

Depend on abstractions instead of concrete implementations.

Bad:

```java
class PedidoService {
private MysqlRepository repository = new MysqlRepository();
}
```

Good:

```java
class PedidoService {
private final PedidoRepository repository;
}
```

---

# 6. Method Design Rules

Methods must follow these constraints:

1. Maximum responsibility: one task.
2. Prefer small methods.
3. Avoid deeply nested logic.
4. Avoid duplicated logic.
5. Extract reusable methods.

---

# 7. Naming Conventions

Use clear and meaningful names.

Good examples:

```java
numero
listaPares
pedidoRepository
obterPedidosAtivos
```

Bad examples:

```java
var1
tmp
x
data2
```

---

# 8. Code Style Rules

The AI must produce code that is:

- minimal
- readable
- maintainable

Avoid:

- unnecessary comments
- redundant loops
- duplicated logic
- unnecessary complexity

---

# 9. Example

Bad example (AI-style verbose code):

```java
for (Integer numero : numeros) {
    if (numero % 2 == 0) {
    System.out.println(numero + " is even");
    } else {
    System.out.println(numero + " is odd");
    }
}
```

Preferred:

```java
public static List<Integer> obterPares(List<Integer> lista) {
    return lista.stream()
    .filter(n -> n % 2 == 0)
    .collect(Collectors.toList());
}
```

---

# 10. Final Instruction for AI

When generating Java code:

1. Follow Clean Code principles.
2. Follow SOLID principles strictly.
3. Avoid unnecessary try/catch blocks.
4. Prefer `if` and `switch` instead of complex `else` chains.
5. Write code like an experienced human developer.
6. Do not generate verbose AI-style code.

# 11. Issue Management (MANDATORY)

All bugs, features, and improvements MUST be tracked via issues.

## Issue Creation Rules

- Create issue document before implementing fix
- Issue format: ISSUE_XXX_DESCRIPTION.md
- Place in project root directory
- No emojis in issue documents
- Professional technical writing only

## Issue Structure

```markdown
# Issue: Brief Title

## Description
Detailed description of the problem

## Current Behavior
What currently happens

## Expected Behavior
What should happen

## Technical Details
- File: path/to/file
- Function: function_name()
- Current value: X
- Target value: Y

## Solution
1. Step 1
2. Step 2

## Files Modified
- file1.rs - Lines X-Y

## Status
Open/In Progress/Closed

## Priority
Low/Medium/High/Critical
```

## Commit Message Rules

- Must reference issue: "Fix: Description (Closes ISSUE_XXX)"
- Update issue status after commit
- Keep commit messages concise and descriptive

## Example Workflow

1. Create ISSUE_001_BANNER_RESOLUTION.md
2. Implement fix
3. Commit: "Fix: Banner resolution increased to 150px (Closes ISSUE_001)"
4. Update issue status to Closed

---

# 12. Final Instruction for AI

When generating Java code for Minecraft Forge mods:

1. Follow Java idioms strictly.
2. Follow SOLID principles strictly.
3. Use proper error handling.
4. Write code like an experienced Minecraft mod developer.
5. Optimize for performance and memory efficiency.
6. Do not generate verbose AI-style code.
7. Create issue documents for all fixes and features.
8. Reference issues in commit messages.
9. Follow Mixin best practices from SpongePowered documentation.

---

# 13. Mixin Guidelines (MANDATORY)

All Mixin code MUST follow SpongePowered best practices: https://docs.spongepowered.org

## Mixin Structure Rules

1. Use @Inject for non-invasive modifications
2. Use @ModifyVariable for variable modifications
3. Use @Redirect for method call redirections
4. Avoid @Overwrite unless absolutely necessary
5. Always specify priority when needed
6. Use cancellable = true only when required
7. Keep mixin methods focused and minimal

## Mixin Naming Conventions

- Mixin class: TargetClassMixin
- Mixin method: descriptive name matching purpose
- Use proper @At injection points

## Mixin Best Practices

```java
@Mixin(TargetClass.class)
public class TargetClassMixin {
    
    @Inject(method = "targetMethod", at = @At("HEAD"), cancellable = true)
    private void onTargetMethod(CallbackInfo ci) {
        // Minimal logic here
    }
}
```

## Avoid

- Complex logic inside mixins
- Multiple responsibilities in one mixin
- Overwriting entire methods
- Unnecessary casts
- Verbose variable names

## Extract Logic

Extract complex logic to separate service classes:

```java
@Mixin(Entity.class)
public class EntityMixin {
    
    @Inject(method = "tick", at = @At("HEAD"))
    private void onTick(CallbackInfo ci) {
        EntityService.handleTick((Entity)(Object)this);
    }
}
```