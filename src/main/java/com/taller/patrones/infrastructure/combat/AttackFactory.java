package com.taller.patrones.infrastructure.combat;

import com.taller.patrones.domain.Attack;
import com.taller.patrones.domain.SimpleAttack;
import java.util.HashMap;
import java.util.Map;

/**
 * Fábrica de ataques que permite registrar nuevos ataques sin modificar código
 * existente.
 * Implementa el patrón Factory Method con registro dinámico.
 * <p>
 * Beneficios:
 * - Cumple con el Principio Abierto/Cerrado (Open/Closed Principle)
 * - Permite añadir nuevos ataques sin modificar CombatEngine
 * - Centraliza la lógica de creación de ataques
 */
public class AttackFactory {

    private final Map<String, Attack> attackRegistry = new HashMap<>();
    private final Attack defaultAttack;

    public AttackFactory() {
        // Registrar los ataques existentes como SimpleAttack
        registerAttack("TACKLE", new SimpleAttack("Tackle", 40, Attack.AttackType.NORMAL));
        registerAttack("SLASH", new SimpleAttack("Slash", 55, Attack.AttackType.NORMAL));
        registerAttack("FIREBALL", new SimpleAttack("Fireball", 80, Attack.AttackType.SPECIAL));
        registerAttack("ICE_BEAM", new SimpleAttack("Ice Beam", 70, Attack.AttackType.SPECIAL));
        registerAttack("POISON_STING", new SimpleAttack("Poison Sting", 20, Attack.AttackType.STATUS));
        registerAttack("THUNDER", new SimpleAttack("Thunder", 90, Attack.AttackType.SPECIAL));

        // Nuevo ataque Meteoro
        registerAttack("METEORO", new SimpleAttack("Meteoro", 120, Attack.AttackType.SPECIAL));

        // Ataques críticos (daño x1.5, probabilidad 20%)
        registerAttack("CRITICAL_HIT", new SimpleAttack("Critical Hit", 60, Attack.AttackType.CRITICAL));
        registerAttack("DRAGON_CLAW", new SimpleAttack("Dragon Claw", 85, Attack.AttackType.CRITICAL));
        registerAttack("DEADLY_STRIKE", new SimpleAttack("Deadly Strike", 100, Attack.AttackType.CRITICAL));

        // Ataque por defecto
        this.defaultAttack = new SimpleAttack("Golpe", 30, Attack.AttackType.NORMAL);
    }

    /**
     * Registra un nuevo ataque en la fábrica.
     * Permite añadir ataques dinámicamente sin modificar esta clase.
     */
    public void registerAttack(String key, Attack attack) {
        attackRegistry.put(key.toUpperCase(), attack);
    }

    /**
     * Crea un ataque a partir de su nombre.
     * Si no existe, devuelve el ataque por defecto.
     */
    public Attack createAttack(String name) {
        String key = name != null ? name.toUpperCase() : "";
        return attackRegistry.getOrDefault(key, defaultAttack);
    }
}
