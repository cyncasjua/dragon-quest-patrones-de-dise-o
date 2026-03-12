package com.taller.patrones.interfaces.rest.dto;

/**
 * DTO interno: representa los datos de batalla en el formato de nuestro dominio.
 * Los Adapters convierten formatos externos a este DTO unificado.
 */
public class BattleData {
    private String fighter1Name;
    private int fighter1Hp;
    private int fighter1Attack;
    private String fighter2Name;
    private int fighter2Hp;
    private int fighter2Attack;

    public BattleData(String fighter1Name, int fighter1Hp, int fighter1Attack,
                      String fighter2Name, int fighter2Hp, int fighter2Attack) {
        this.fighter1Name = fighter1Name;
        this.fighter1Hp = fighter1Hp;
        this.fighter1Attack = fighter1Attack;
        this.fighter2Name = fighter2Name;
        this.fighter2Hp = fighter2Hp;
        this.fighter2Attack = fighter2Attack;
    }

    public String getFighter1Name() {
        return fighter1Name;
    }

    public int getFighter1Hp() {
        return fighter1Hp;
    }

    public int getFighter1Attack() {
        return fighter1Attack;
    }

    public String getFighter2Name() {
        return fighter2Name;
    }

    public int getFighter2Hp() {
        return fighter2Hp;
    }

    public int getFighter2Attack() {
        return fighter2Attack;
    }
}
