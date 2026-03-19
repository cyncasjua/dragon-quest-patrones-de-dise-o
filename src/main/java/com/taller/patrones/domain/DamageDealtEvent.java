package com.taller.patrones.domain;

public class DamageDealtEvent {
    private final Battle battle;
    private final Character attacker;
    private final Character defender;
    private final int damage;
    private final Attack attack;

    public DamageDealtEvent(Battle battle, Character attacker, Character defender, int damage, Attack attack) {
        this.battle = battle;
        this.attacker = attacker;
        this.defender = defender;
        this.damage = damage;
        this.attack = attack;
    }

    public Battle getBattle() { return battle; }
    public Character getAttacker() { return attacker; }
    public Character getDefender() { return defender; }
    public int getDamage() { return damage; }
    public Attack getAttack() { return attack; }
}
