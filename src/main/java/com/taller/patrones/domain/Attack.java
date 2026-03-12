package com.taller.patrones.domain;

/**
 * Representa un ataque que puede ejecutar un personaje.
 */
public class Attack {

    private final String name;
    private final int basePower;
    private final AttackType type;

    public Attack(String name, int basePower, AttackType type) {
        this.name = name;
        this.basePower = basePower;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getBasePower() {
        return basePower;
    }

    public AttackType getType() {
        return type;
    }

    public enum AttackType {
        NORMAL, // Ataque físico estándar
        SPECIAL, // Ataque que ignora parte de la defensa
        STATUS, // Ataques de estado (veneno, parálisis) - no hacen daño directo
        CRITICAL // Ataque crítico con daño aumentado (x1.5)
    }
}
