package com.taller.patrones.infrastructure.combat;

import com.taller.patrones.domain.Attack;
import com.taller.patrones.domain.Attack.AttackType;
import com.taller.patrones.domain.Character;

import java.util.HashMap;
import java.util.Map;

/**
 * Motor de combate. Calcula daño y crea ataques.
 * <p>
 * Refactorizado con patrón Strategy para cálculo de daño.
 * Cada tipo de ataque tiene su propia estrategia de daño,
 * permitiendo añadir nuevas fórmulas sin modificar esta clase.
 * <p>
 * Principios SOLID aplicados:
 * - Open/Closed: Abierto a extensión (nuevas estrategias), cerrado a
 * modificación
 * - Single Responsibility: Delega el cálculo de daño a las estrategias
 */
public class CombatEngine {

    private final AttackFactory attackFactory;
    private final Map<AttackType, DamageStrategy> damageStrategies;

    public CombatEngine() {
        this.attackFactory = new AttackFactory();
        this.damageStrategies = new HashMap<>();
        initializeDamageStrategies();
    }

    /**
     * Inicializa las estrategias de daño para cada tipo de ataque.
     * Para añadir nuevas fórmulas, solo hay que crear una nueva estrategia
     * y registrarla aquí.
     */
    private void initializeDamageStrategies() {
        damageStrategies.put(AttackType.NORMAL, new NormalDamageStrategy());
        damageStrategies.put(AttackType.SPECIAL, new SpecialDamageStrategy());
        damageStrategies.put(AttackType.STATUS, new StatusDamageStrategy());
        damageStrategies.put(AttackType.CRITICAL, new CriticalDamageStrategy());
    }

    /**
     * Crea un ataque a partir de su nombre usando la factory.
     * Ya no necesita ser modificado cuando se añaden nuevos ataques.
     */
    public Attack createAttack(String name) {
        return attackFactory.createAttack(name);
    }

    /**
     * Calcula el daño usando el patrón Strategy.
     * <p>
     * Ya no necesita modificarse cuando se añaden nuevos tipos de ataque.
     * Cada tipo usa su estrategia de daño correspondiente.
     * 
     * @param attacker personaje atacante
     * @param defender personaje defensor
     * @param attack   ataque utilizado
     * @return daño calculado según la estrategia del tipo de ataque
     */
    public int calculateDamage(Character attacker, Character defender, Attack attack) {
        DamageStrategy strategy = damageStrategies.get(attack.getType());

        if (strategy == null) {
            // Fallback: si no hay estrategia definida, no hace daño
            return 0;
        }

        return strategy.calculateDamage(attacker, defender, attack);
    }
}
