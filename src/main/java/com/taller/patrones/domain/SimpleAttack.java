package com.taller.patrones.domain;

/**
 * Implementación concreta de un ataque simple.
 */
public class SimpleAttack implements Attack {
    private final String name;
    private final int basePower;
    private final AttackType type;

    public SimpleAttack(String name, int basePower, AttackType type) {
        this.name = name;
        this.basePower = basePower;
        this.type = type;
    }

    @Override
    public void executeDamage(Character attacker, Character defender) {
        // La lógica real se delega en el sistema de combate (Strategy)
        // Aquí no se implementa nada, se usa para compatibilidad con ComboAttack
        // y para cumplir la interfaz.
        // Normalmente, el motor de combate calculará el daño.
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getBasePower() {
        return basePower;
    }

    @Override
    public AttackType getType() {
        return type;
    }
}