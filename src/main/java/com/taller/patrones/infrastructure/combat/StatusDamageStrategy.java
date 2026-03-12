package com.taller.patrones.infrastructure.combat;

import com.taller.patrones.domain.Attack;
import com.taller.patrones.domain.Character;

/**
 * Estrategia de daño para ataques de estado (veneno, parálisis, etc.).
 * <p>
 * Los ataques de estado NO hacen daño directo.
 * Su efecto principal es aplicar condiciones de estado al objetivo.
 */
public class StatusDamageStrategy implements DamageStrategy {

    @Override
    public int calculateDamage(Character attacker, Character defender, Attack attack) {
        // Los ataques de estado no hacen daño directo
        return 0;
    }
}
