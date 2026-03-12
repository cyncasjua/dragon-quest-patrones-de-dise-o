package com.taller.patrones.infrastructure.combat;

import com.taller.patrones.domain.Attack;
import com.taller.patrones.domain.Character;

/**
 * Patrón Strategy: Estrategia de cálculo de daño.
 * <p>
 * Permite encapsular diferentes fórmulas de daño en clases separadas.
 * Cada tipo de ataque puede tener su propia estrategia de cálculo.
 * <p>
 * Principio SOLID aplicado: Open/Closed Principle (OCP).
 * Las nuevas fórmulas se añaden creando nuevas estrategias,
 * sin modificar código existente.
 */
public interface DamageStrategy {

    /**
     * Calcula el daño que inflige un ataque.
     * 
     * @param attacker personaje atacante
     * @param defender personaje defensor
     * @param attack   ataque utilizado
     * @return cantidad de daño a infligir
     */
    int calculateDamage(Character attacker, Character defender, Attack attack);
}
