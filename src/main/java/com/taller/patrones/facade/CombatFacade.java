package com.taller.patrones.facade;

import com.taller.patrones.application.BattleService;
import com.taller.patrones.domain.Attack;
import com.taller.patrones.domain.Battle;
import com.taller.patrones.domain.Character;

/**
 * Fachada para simplificar la interacción con el sistema de combate.
 */
public class CombatFacade {
    private final BattleService battleService;

    public CombatFacade(BattleService battleService) {
        this.battleService = battleService;
    }

    /**
     * Inicia una nueva batalla entre jugador y enemigo.
     */
    public BattleService.BattleStartResult iniciarBatalla(String nombreJugador, String nombreEnemigo) {
        return battleService.startBattle(nombreJugador, nombreEnemigo);
    }

    /**
     * Ejecuta el ataque del jugador en la batalla.
     */
    public void atacarJugador(String battleId, String nombreAtaque) {
        battleService.executePlayerAttack(battleId, nombreAtaque);
    }

    /**
     * Ejecuta el ataque del enemigo en la batalla.
     */
    public void atacarEnemigo(String battleId, String nombreAtaque) {
        battleService.executeEnemyAttack(battleId, nombreAtaque);
    }

    /**
     * Deshace el último ataque realizado en la batalla.
     */
    public void deshacerUltimoAtaque() {
        battleService.undoLastAttack();
    }

    /**
     * Obtiene el estado actual de la batalla.
     */
    public Battle obtenerBatalla(String battleId) {
        return battleService.getBattle(battleId);
    }
}
