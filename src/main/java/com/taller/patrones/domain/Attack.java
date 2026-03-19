package com.taller.patrones.domain;

/**
 * Interfaz para representar cualquier tipo de ataque (simple o compuesto).
 */
public interface Attack {
    void executeDamage(Character attacker, Character defender);

    String getName();

    int getBasePower();

    AttackType getType();

    enum AttackType {
        NORMAL, // Ataque físico estándar
        SPECIAL, // Ataque que ignora parte de la defensa
        STATUS, // Ataques de estado (veneno, parálisis) - no hacen daño directo
        CRITICAL // Ataque crítico con daño aumentado (x1.5)
    }
}
