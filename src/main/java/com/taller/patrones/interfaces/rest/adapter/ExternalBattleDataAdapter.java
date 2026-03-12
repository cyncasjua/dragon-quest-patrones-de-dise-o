package com.taller.patrones.interfaces.rest.adapter;

import com.taller.patrones.interfaces.rest.dto.BattleData;

/**
 * Patrón Adapter: Define la interfaz común para adaptar diferentes formatos
 * externos
 * a nuestro modelo de dominio.
 * 
 * Cada proveedor de API externa puede tener su propio formato (JSON con nombres
 * distintos,
 * estructuras anidadas, etc.), pero todos deben poder convertirse a BattleData
 * para ser
 * procesados por nuestro sistema.
 */
public interface ExternalBattleDataAdapter<T> {

    /**
     * Convierte un objeto de formato externo a nuestro formato interno BattleData.
     * 
     * @param externalData Los datos en el formato del proveedor externo
     * @return BattleData con los datos traducidos a nuestro dominio
     */
    BattleData adaptToBattleData(T externalData);
}
