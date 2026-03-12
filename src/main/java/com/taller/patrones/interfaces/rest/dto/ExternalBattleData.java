package com.taller.patrones.interfaces.rest.dto;

/**
 * DTO para el formato "estándar" del proveedor 1.
 * Usa nombres como fighter1_hp, fighter1_atk, fighter2_name, etc.
 */
public class ExternalBattleData {
    private String fighter1_name;
    private Integer fighter1_hp;
    private Integer fighter1_atk;
    private String fighter2_name;
    private Integer fighter2_hp;
    private Integer fighter2_atk;

    public ExternalBattleData() {
    }

    public String getFighter1_name() {
        return fighter1_name;
    }

    public void setFighter1_name(String fighter1_name) {
        this.fighter1_name = fighter1_name;
    }

    public Integer getFighter1_hp() {
        return fighter1_hp;
    }

    public void setFighter1_hp(Integer fighter1_hp) {
        this.fighter1_hp = fighter1_hp;
    }

    public Integer getFighter1_atk() {
        return fighter1_atk;
    }

    public void setFighter1_atk(Integer fighter1_atk) {
        this.fighter1_atk = fighter1_atk;
    }

    public String getFighter2_name() {
        return fighter2_name;
    }

    public void setFighter2_name(String fighter2_name) {
        this.fighter2_name = fighter2_name;
    }

    public Integer getFighter2_hp() {
        return fighter2_hp;
    }

    public void setFighter2_hp(Integer fighter2_hp) {
        this.fighter2_hp = fighter2_hp;
    }

    public Integer getFighter2_atk() {
        return fighter2_atk;
    }

    public void setFighter2_atk(Integer fighter2_atk) {
        this.fighter2_atk = fighter2_atk;
    }
}
