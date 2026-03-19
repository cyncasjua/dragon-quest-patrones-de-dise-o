package com.taller.patrones.domain;

import java.util.List;

/**
 * Representa un ataque compuesto (combo) que ejecuta varios ataques
 * individuales.
 * Patrón Composite.
 */
public class ComboAttack implements Attack {
    private final List<Attack> attacks;

    public ComboAttack(List<Attack> attacks) {
        this.attacks = attacks;
    }

    @Override
    public void executeDamage(Character attacker, Character defender) {
        for (Attack attack : attacks) {
            attack.executeDamage(attacker, defender);
        }
    }

    @Override
    public String getName() {
        return "Combo: " + String.join(", ", attacks.stream().map(Attack::getName).toList());
    }

    @Override
    public int getBasePower() {
        // Suma de los poderes base de los ataques
        return attacks.stream().mapToInt(Attack::getBasePower).sum();
    }

    @Override
    public AttackType getType() {
        // Por simplicidad, se puede considerar como SPECIAL o el tipo del primer ataque
        return attacks.isEmpty() ? AttackType.SPECIAL : attacks.get(0).getType();
    }
}
