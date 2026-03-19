package com.taller.patrones.application;

import com.taller.patrones.domain.Attack;
import com.taller.patrones.domain.Battle;
import com.taller.patrones.domain.Character;
import com.taller.patrones.infrastructure.combat.CombatEngine;
import com.taller.patrones.infrastructure.persistence.BattleRepository;

import java.util.List;
import java.util.UUID;
import java.util.ArrayList;
import com.taller.patrones.domain.DamageDealtListener;
import com.taller.patrones.domain.DamageDealtEvent;

/**
 * Caso de uso: gestionar batallas.
 * <p>
 * Nota: Crea sus propias dependencias con new. Cada vez que necesitamos
 * un CombatEngine o BattleRepository, hacemos new aquí.
 * BattleRepository usa Singleton para garantizar una única instancia
 * compartida.
 */
public class BattleService {

    // Lista de observadores del evento de daño
    private final List<DamageDealtListener> damageDealtListeners = new ArrayList<>();

    // Permite registrar listeners
    public void addDamageDealtListener(DamageDealtListener listener) {
        damageDealtListeners.add(listener);
    }

    // Notifica a todos los listeners cuando ocurre daño
    private void notifyDamageDealt(Battle battle, Character attacker, Character defender, int damage, Attack attack) {
        DamageDealtEvent event = new DamageDealtEvent(battle, attacker, defender, damage, attack);
        for (DamageDealtListener listener : damageDealtListeners) {
            listener.onDamageDealt(event);
        }
    }

    private final CombatEngine combatEngine = new CombatEngine();
    private final BattleRepository battleRepository = BattleRepository.getInstance();

    public static final List<String> PLAYER_ATTACKS = List.of("TACKLE", "SLASH", "FIREBALL", "ICE_BEAM", "POISON_STING",
            "THUNDER");
    public static final List<String> ENEMY_ATTACKS = List.of("TACKLE", "SLASH", "FIREBALL");

    public BattleStartResult startBattle(String playerName, String enemyName) {
        // Uso del patrón Builder: construcción clara y legible
        Character player = new Character.CharacterBuilder()
                .withName(playerName != null ? playerName : "Héroe")
                .withHp(150)
                .withAttack(25)
                .withDefense(15)
                .withSpeed(20)
                .withClass("Guerrero")
                .build();

        Character enemy = new Character.CharacterBuilder()
                .withName(enemyName != null ? enemyName : "Dragón")
                .withHp(120)
                .withAttack(30)
                .withDefense(10)
                .withSpeed(15)
                .withClass("Bestia")
                .build();

        Battle battle = new Battle(player, enemy);
        String battleId = UUID.randomUUID().toString();
        battleRepository.save(battleId, battle);

        return new BattleStartResult(battleId, battle);
    }

    public Battle getBattle(String battleId) {
        return battleRepository.findById(battleId);
    }

    public void executePlayerAttack(String battleId, String attackName) {
        Battle battle = battleRepository.findById(battleId);
        if (battle == null || battle.isFinished() || !battle.isPlayerTurn())
            return;

        Attack attack = combatEngine.createAttack(attackName);
        int damage = combatEngine.calculateDamage(battle.getPlayer(), battle.getEnemy(), attack);
        applyDamage(battle, battle.getPlayer(), battle.getEnemy(), damage, attack);
    }

    public void executeEnemyAttack(String battleId, String attackName) {
        Battle battle = battleRepository.findById(battleId);
        if (battle == null || battle.isFinished() || battle.isPlayerTurn())
            return;

        Attack attack = combatEngine.createAttack(attackName != null ? attackName : "TACKLE");
        int damage = combatEngine.calculateDamage(battle.getEnemy(), battle.getPlayer(), attack);
        applyDamage(battle, battle.getEnemy(), battle.getPlayer(), damage, attack);
    }

    private void applyDamage(Battle battle, Character attacker, Character defender, int damage, Attack attack) {
        defender.takeDamage(damage);
        String target = defender == battle.getPlayer() ? "player" : "enemy";
        battle.setLastDamage(damage, target);
        battle.log(attacker.getName() + " usa " + attack.getName() + " y hace " + damage + " de daño a "
                + defender.getName());
        // Notificar a los observadores
        notifyDamageDealt(battle, attacker, defender, damage, attack);
        battle.switchTurn();
        if (!defender.isAlive()) {
            battle.finish(attacker.getName());
        }
    }

    public BattleStartResult startBattleFromExternal(String fighter1Name, int fighter1Hp, int fighter1Atk,
            String fighter2Name, int fighter2Hp, int fighter2Atk) {
        // Builder con valores por defecto para defense y speed (solo configuramos lo
        // necesario)
        Character player = new Character.CharacterBuilder()
                .withName(fighter1Name)
                .withHp(fighter1Hp)
                .withAttack(fighter1Atk)
                .build(); // defense y speed usan valores por defecto (10)

        Character enemy = new Character.CharacterBuilder()
                .withName(fighter2Name)
                .withHp(fighter2Hp)
                .withAttack(fighter2Atk)
                .build();

        Battle battle = new Battle(player, enemy);
        String battleId = UUID.randomUUID().toString();
        battleRepository.save(battleId, battle);
        return new BattleStartResult(battleId, battle);
    }

    public record BattleStartResult(String battleId, Battle battle) {
    }
}
