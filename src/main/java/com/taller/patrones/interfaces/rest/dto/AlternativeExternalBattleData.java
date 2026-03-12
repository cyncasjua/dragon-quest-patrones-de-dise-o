package com.taller.patrones.interfaces.rest.dto;

/**
 * DTO para el formato "alternativo" del proveedor 2.
 * Usa una estructura anidada: player.health, player.attack, enemy.health, etc.
 */
public class AlternativeExternalBattleData {
    private PlayerData player;
    private EnemyData enemy;

    public AlternativeExternalBattleData() {
    }

    public PlayerData getPlayer() {
        return player;
    }

    public void setPlayer(PlayerData player) {
        this.player = player;
    }

    public EnemyData getEnemy() {
        return enemy;
    }

    public void setEnemy(EnemyData enemy) {
        this.enemy = enemy;
    }

    public static class PlayerData {
        private String name;
        private Integer health;
        private Integer attack;

        public PlayerData() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getHealth() {
            return health;
        }

        public void setHealth(Integer health) {
            this.health = health;
        }

        public Integer getAttack() {
            return attack;
        }

        public void setAttack(Integer attack) {
            this.attack = attack;
        }
    }

    public static class EnemyData {
        private String name;
        private Integer health;
        private Integer attack;

        public EnemyData() {
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getHealth() {
            return health;
        }

        public void setHealth(Integer health) {
            this.health = health;
        }

        public Integer getAttack() {
            return attack;
        }

        public void setAttack(Integer attack) {
            this.attack = attack;
        }
    }
}
