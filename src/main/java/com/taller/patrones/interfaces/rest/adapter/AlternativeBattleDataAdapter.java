package com.taller.patrones.interfaces.rest.adapter;

import com.taller.patrones.interfaces.rest.dto.AlternativeExternalBattleData;
import com.taller.patrones.interfaces.rest.dto.BattleData;

/**
 * Adaptador concreto para el formato "alternativo" del proveedor 2.
 * Convierte AlternativeExternalBattleData (player.health, enemy.attack...) a
 * BattleData.
 * 
 * Ventaja del patrón Adapter: Al agregar un nuevo proveedor, solo necesitas
 * crear
 * una nueva clase adaptadora sin modificar el controller ni el servicio.
 */
public class AlternativeBattleDataAdapter implements ExternalBattleDataAdapter<AlternativeExternalBattleData> {

    @Override
    public BattleData adaptToBattleData(AlternativeExternalBattleData externalData) {
        // Validar que los datos anidados no sean null
        if (externalData.getPlayer() == null || externalData.getEnemy() == null) {
            throw new IllegalArgumentException("Los datos de player y enemy son obligatorios");
        }

        var playerData = externalData.getPlayer();
        var enemyData = externalData.getEnemy();

        // Valores por defecto
        String fighter1Name = playerData.getName() != null
                ? playerData.getName()
                : "Héroe";
        int fighter1Hp = playerData.getHealth() != null
                ? playerData.getHealth()
                : 150;
        int fighter1Attack = playerData.getAttack() != null
                ? playerData.getAttack()
                : 25;

        String fighter2Name = enemyData.getName() != null
                ? enemyData.getName()
                : "Dragón";
        int fighter2Hp = enemyData.getHealth() != null
                ? enemyData.getHealth()
                : 120;
        int fighter2Attack = enemyData.getAttack() != null
                ? enemyData.getAttack()
                : 30;

        return new BattleData(
                fighter1Name, fighter1Hp, fighter1Attack,
                fighter2Name, fighter2Hp, fighter2Attack);
    }
}
