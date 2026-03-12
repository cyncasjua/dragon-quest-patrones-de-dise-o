package com.taller.patrones.infrastructure.combat;

import com.taller.patrones.domain.Attack;
import com.taller.patrones.domain.Character;

/**
 * Estrategia de daño crítico.
 * <p>
 * Fórmula: daño normal * 1.5
 * Probabilidad: 20% de activarse (se maneja en otro nivel)
 * <p>
 * El ataque crítico aplica un multiplicador al daño base,
 * ignorando la defensa del objetivo.
 */
public class CriticalDamageStrategy implements DamageStrategy {

    private static final double CRITICAL_MULTIPLIER = 1.5;

    @Override
    public int calculateDamage(Character attacker, Character defender, Attack attack) {
        int raw = attacker.getAttack() * attack.getBasePower() / 100;
        int criticalDamage = (int) (raw * CRITICAL_MULTIPLIER);
        // Los críticos ignoran defensa pero tienen mínimo 1
        return Math.max(1, criticalDamage);
    }
}
