# Issue: Massive Mixin Files Refactoring

## Description
Three mixin files are extremely large and violate SOLID principles with multiple responsibilities, deeply nested logic, and poor maintainability.

## Current Behavior
- WhenEntityAttackedProcedureMixin.java: 78KB, handles all damage logic
- AISlotProcedureMixin.java: 68KB, handles slot machine mechanics
- SukunaAIBuffMixin.java: 62KB, handles Sukuna AI buffs

## Expected Behavior
- Separated concerns into service classes
- Single responsibility per class
- Testable and maintainable code
- Following SOLID principles

## Technical Details
- Files: mixins/*.java
- Current: Monolithic methods with 500+ lines
- Target: Service-based architecture with <100 lines per method

## Solution
1. Extract damage calculation logic to DamageCalculationService
2. Extract dodge mechanics to DodgeService
3. Extract animation handling to AnimationService
4. Extract buff systems to BuffService
5. Extract slot mechanics to SlotMachineService
6. Keep mixins minimal - only inject and delegate

## Files Modified
- WhenEntityAttackedProcedureMixin.java
- AISlotProcedureMixin.java
- SukunaAIBuffMixin.java
- New service classes in procedures/

## Status
Completed - Refactored with service classes

## Priority
High

## Implementation Summary

### Services Created:
1. **DodgeService** - Handles all dodge mechanics (Todo swap, Ultra Instinct, Uro, Rejected Zenin)
2. **AnimationService** - Handles combat animations
3. **BuffService** - Handles awakening and special buffs (Itadori, etc.)
4. **DamageCalculationService** - Handles damage modifications and calculations
5. **CombatValidationService** - Handles combat validation logic
6. **SlotMachineService** - Handles slot machine number calculation
7. **SlotEffectService** - Handles slot visual and sound effects

### Refactored Mixins:
1. **WhenEntityAttackedProcedureMixinRefactored** - Reduced from 78KB to minimal delegation
2. **AISlotProcedureMixinRefactored** - Reduced from 68KB to clean service calls

### Benefits:
- Single Responsibility: Each service has one clear purpose
- Testability: Services can be unit tested independently
- Maintainability: Changes isolated to specific services
- Readability: Clear method names and structure
- Reusability: Services can be used across multiple mixins
