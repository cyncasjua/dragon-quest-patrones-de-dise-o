package com.taller.patrones.infrastructure.persistence;

import com.taller.patrones.domain.Battle;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Almacena las batallas activas en memoria.
 * <p>
 * Patrón Singleton: garantiza una única instancia de BattleRepository en toda
 * la aplicación, asegurando que todos los servicios compartan el mismo almacén.
 */
public class BattleRepository {

    private static final Map<String, Battle> battles = new ConcurrentHashMap<>();
    private static final BattleRepository instance = new BattleRepository();

    /**
     * Constructor privado para evitar instanciación externa.
     * Solo getInstance() puede acceder a la única instancia.
     */
    private BattleRepository() {
    }

    /**
     * Obtiene la única instancia de BattleRepository.
     *
     * @return la instancia única (Singleton)
     */
    public static BattleRepository getInstance() {
        return instance;
    }

    public void save(String id, Battle battle) {
        battles.put(id, battle);
    }

    public Battle findById(String id) {
        return battles.get(id);
    }

    public void remove(String id) {
        battles.remove(id);
    }
}
