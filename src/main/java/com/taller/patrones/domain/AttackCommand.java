package com.taller.patrones.domain;

import com.taller.patrones.domain.Character;
import com.taller.patrones.domain.Attack;
import com.taller.patrones.domain.Battle;
import com.taller.patrones.infrastructure.combat.CombatEngine;

/**
 * Comando que encapsula un ataque y permite deshacerlo.
 */
public class AttackCommand implements Command {
    private final Battle battle;
    private final Character attacker;
    private final Character defender;
    private final Attack attack;
    private final CombatEngine combatEngine;
    // Estado previo para undo
    private int previousHp;
    private String previousTurn;
    private boolean wasFinished;
    private int previousLastDamage;
    private String previousLastDamageTarget;

    private int damageDealt;

    public AttackCommand(Battle battle, Character attacker, Character defender, Attack attack,
            CombatEngine combatEngine) {
        this.battle = battle;
        this.attacker = attacker;
        this.defender = defender;
        this.attack = attack;
        this.combatEngine = combatEngine;
    }

    @Override
    public void execute() {
        // Guardar estado previo
        previousHp = defender.getCurrentHp();
        previousTurn = battle.getCurrentTurn();
        wasFinished = battle.isFinished();
        previousLastDamage = battle.getLastDamage();
        previousLastDamageTarget = battle.getLastDamageTarget();

        // Calcular y aplicar daño
        damageDealt = combatEngine.calculateDamage(attacker, defender, attack);
        defender.takeDamage(damageDealt);
        String target = defender == battle.getPlayer() ? "player" : "enemy";
        battle.setLastDamage(damageDealt, target);
        battle.log(attacker.getName() + " usa " + attack.getName() + " y hace " + damageDealt + " de daño a "
                + defender.getName());
        battle.switchTurn();
        if (!defender.isAlive()) {
            battle.finish(attacker.getName());
        }
    }

    @Override
    public void undo() {
        // Restaurar estado previo
        defender.setCurrentHp(previousHp); // restaurar vida exacta
        // Restaurar turno y estado de batalla de forma segura
        battle.setCurrentTurn(previousTurn);
        battle.setFinished(wasFinished);
        battle.setLastDamage(previousLastDamage, previousLastDamageTarget);
        battle.removeLastLog();
    }
}