package com.taller.patrones.interfaces.rest;

import com.taller.patrones.application.BattleService;
import com.taller.patrones.facade.CombatFacade;
import com.taller.patrones.domain.Battle;
import com.taller.patrones.domain.Character;
import com.taller.patrones.interfaces.rest.adapter.AlternativeBattleDataAdapter;
import com.taller.patrones.interfaces.rest.adapter.ExternalBattleDataAdapter;
import com.taller.patrones.interfaces.rest.adapter.StandardBattleDataAdapter;
import com.taller.patrones.interfaces.rest.dto.AlternativeExternalBattleData;
import com.taller.patrones.interfaces.rest.dto.BattleData;
import com.taller.patrones.interfaces.rest.dto.ExternalBattleData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/battle")
@CrossOrigin(origins = "*")
public class BattleController {

    private final BattleService battleService = new BattleService();
    private final CombatFacade combatFacade = new CombatFacade(battleService);

    @PostMapping("/start")
    public ResponseEntity<Map<String, Object>> startBattle(@RequestBody(required = false) Map<String, String> body) {
        String playerName = body != null && body.containsKey("playerName") ? body.get("playerName") : null;
        String enemyName = body != null && body.containsKey("enemyName") ? body.get("enemyName") : null;

        var result = combatFacade.iniciarBatalla(playerName, enemyName);
        Battle battle = result.battle();

        return ResponseEntity.ok(Map.of(
                "battleId", result.battleId(),
                "player", toCharacterDto(battle.getPlayer()),
                "enemy", toCharacterDto(battle.getEnemy()),
                "currentTurn", battle.getCurrentTurn(),
                "battleLog", battle.getBattleLog(),
                "finished", battle.isFinished(),
                "playerAttacks", BattleService.PLAYER_ATTACKS,
                "enemyAttacks", BattleService.ENEMY_ATTACKS,
                "lastDamage", 0,
                "lastDamageTarget", ""));
    }

    /**
     * Endpoint: recibe datos de combate en formato "estándar" del proveedor 1.
     * Los campos vienen como: fighter1_hp, fighter1_atk, fighter2_name, etc.
     * 
     * Patrón Adapter en acción:
     * - El controller NO hace conversión manual
     * - Delega al StandardBattleDataAdapter la traducción del formato externo
     * - El controller solo orquesta: recibe DTO → adapta → llama al servicio
     * 
     * Ejemplo de JSON:
     * {
     * "fighter1_name": "Link",
     * "fighter1_hp": 180,
     * "fighter1_atk": 30,
     * "fighter2_name": "Ganon",
     * "fighter2_hp": 200,
     * "fighter2_atk": 35
     * }
     */
    @PostMapping("/start/external")
    public ResponseEntity<Map<String, Object>> startBattleFromExternal(@RequestBody ExternalBattleData externalData) {
        // Patrón Adapter: aísla la conversión en una clase específica
        ExternalBattleDataAdapter<ExternalBattleData> adapter = new StandardBattleDataAdapter();
        BattleData battleData = adapter.adaptToBattleData(externalData);

        // El controller solo llama al servicio con datos ya adaptados
        var result = battleService.startBattleFromExternal(
                battleData.getFighter1Name(), battleData.getFighter1Hp(), battleData.getFighter1Attack(),
                battleData.getFighter2Name(), battleData.getFighter2Hp(), battleData.getFighter2Attack());
        Battle battle = result.battle();

        return ResponseEntity.ok(Map.of(
                "battleId", result.battleId(),
                "player", toCharacterDto(battle.getPlayer()),
                "enemy", toCharacterDto(battle.getEnemy()),
                "currentTurn", battle.getCurrentTurn(),
                "battleLog", battle.getBattleLog(),
                "finished", battle.isFinished(),
                "playerAttacks", BattleService.PLAYER_ATTACKS,
                "lastDamage", 0,
                "lastDamageTarget", ""));
    }

    /**
     * Endpoint: recibe datos de combate en formato "alternativo" del proveedor 2.
     * Los campos vienen anidados: player.health, player.attack, enemy.health, etc.
     * 
     * Ventaja del patrón Adapter:
     * - Nuevo proveedor = nuevo adaptador, sin modificar controller ni servicio
     * - Mantiene el principio Open/Closed: abierto a extensión, cerrado a
     * modificación
     * 
     * Ejemplo de JSON:
     * {
     * "player": {
     * "name": "Mario",
     * "health": 150,
     * "attack": 28
     * },
     * "enemy": {
     * "name": "Bowser",
     * "health": 180,
     * "attack": 32
     * }
     * }
     */
    @PostMapping("/start/external/alternative")
    public ResponseEntity<Map<String, Object>> startBattleFromAlternativeExternal(
            @RequestBody AlternativeExternalBattleData externalData) {

        // Patrón Adapter: cada formato tiene su propio adaptador
        ExternalBattleDataAdapter<AlternativeExternalBattleData> adapter = new AlternativeBattleDataAdapter();
        BattleData battleData = adapter.adaptToBattleData(externalData);

        // El mismo código del servicio funciona sin importar el formato de origen
        var result = battleService.startBattleFromExternal(
                battleData.getFighter1Name(), battleData.getFighter1Hp(), battleData.getFighter1Attack(),
                battleData.getFighter2Name(), battleData.getFighter2Hp(), battleData.getFighter2Attack());
        Battle battle = result.battle();

        return ResponseEntity.ok(Map.of(
                "battleId", result.battleId(),
                "player", toCharacterDto(battle.getPlayer()),
                "enemy", toCharacterDto(battle.getEnemy()),
                "currentTurn", battle.getCurrentTurn(),
                "battleLog", battle.getBattleLog(),
                "finished", battle.isFinished(),
                "playerAttacks", BattleService.PLAYER_ATTACKS,
                "lastDamage", 0,
                "lastDamageTarget", ""));
    }

    @GetMapping("/{battleId}")
    public ResponseEntity<Map<String, Object>> getBattle(@PathVariable String battleId) {
        Battle battle = battleService.getBattle(battleId);
        if (battle == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(toBattleDto(battle));
    }

    @PostMapping("/{battleId}/attack")
    public ResponseEntity<Map<String, Object>> attack(@PathVariable String battleId,
            @RequestBody Map<String, String> body) {
        Battle battle = combatFacade.obtenerBatalla(battleId);
        if (battle == null)
            return ResponseEntity.notFound().build();

        String attackName = body != null && body.get("attack") != null ? body.get("attack") : "TACKLE";

        if (battle.isPlayerTurn()) {
            combatFacade.atacarJugador(battleId, attackName);
        } else {
            combatFacade.atacarEnemigo(battleId, attackName);
        }

        return ResponseEntity.ok(toBattleDto(combatFacade.obtenerBatalla(battleId)));
    }

    @PostMapping("/{battleId}/enemy-turn")
    public ResponseEntity<Map<String, Object>> enemyTurn(@PathVariable String battleId) {
        Battle battle = combatFacade.obtenerBatalla(battleId);
        if (battle == null)
            return ResponseEntity.notFound().build();
        if (battle.isPlayerTurn() || battle.isFinished()) {
            return ResponseEntity.ok(toBattleDto(battle));
        }
        String attack = BattleService.ENEMY_ATTACKS.get((int) (Math.random() * BattleService.ENEMY_ATTACKS.size()));
        combatFacade.atacarEnemigo(battleId, attack);
        return ResponseEntity.ok(toBattleDto(combatFacade.obtenerBatalla(battleId)));
    }

    private Map<String, Object> toBattleDto(Battle battle) {
        return Map.of(
                "player", toCharacterDto(battle.getPlayer()),
                "enemy", toCharacterDto(battle.getEnemy()),
                "currentTurn", battle.getCurrentTurn(),
                "battleLog", battle.getBattleLog(),
                "finished", battle.isFinished(),
                "playerAttacks", BattleService.PLAYER_ATTACKS,
                "lastDamage", battle.getLastDamage(),
                "lastDamageTarget", battle.getLastDamageTarget() != null ? battle.getLastDamageTarget() : "");
    }

    private Map<String, Object> toCharacterDto(Character c) {
        return Map.of(
                "name", c.getName(),
                "currentHp", c.getCurrentHp(),
                "maxHp", c.getMaxHp(),
                "hpPercentage", c.getHpPercentage(),
                "attack", c.getAttack(),
                "defense", c.getDefense(),
                "speed", c.getSpeed(),
                "alive", c.isAlive());
    }
}
