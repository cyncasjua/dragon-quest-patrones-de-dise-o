package com.taller.patrones.interfaces.rest.adapter;

import com.taller.patrones.interfaces.rest.dto.BattleData;
import com.taller.patrones.interfaces.rest.dto.ExternalBattleData;

/**
 * Adaptador concreto para el formato "estándar" del proveedor 1.
 * Convierte ExternalBattleData (fighter1_hp, fighter1_atk...) a BattleData.
 * 
 * Responsabilidad: Aislar la lógica de conversión del controller y permitir
 * que el formato externo cambie sin afectar al resto del sistema.
 */
public class StandardBattleDataAdapter implements ExternalBattleDataAdapter<ExternalBattleData> {

    @Override
    public BattleData adaptToBattleData(ExternalBattleData externalData) {
        // Valores por defecto en caso de que falten datos
        String fighter1Name = externalData.getFighter1_name() != null 
            ? externalData.getFighter1_name() : "Héroe";
        int fighter1Hp = externalData.getFighter1_hp() != null 
            ? externalData.getFighter1_hp() : 150;
        int fighter1Attack = externalData.getFighter1_atk() != null 
            ? externalData.getFighter1_atk() : 25;
        
        String fighter2Name = externalData.getFighter2_name() != null 
            ? externalData.getFighter2_name() : "Dragón";
        int fighter2Hp = externalData.getFighter2_hp() != null 
            ? externalData.getFighter2_hp() : 120;
        int fighter2Attack = externalData.getFighter2_atk() != null 
            ? externalData.getFighter2_atk() : 30;

        return new BattleData(
            fighter1Name, fighter1Hp, fighter1Attack,
            fighter2Name, fighter2Hp, fighter2Attack
        );
    }
}
