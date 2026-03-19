
package com.taller.patrones.domain;

/**
 * Representa un personaje en combate.
 * Utiliza el patrón Builder para facilitar la construcción de personajes con
 * múltiples atributos.
 */
public class Character {

    private final String name;
    private int currentHp;
    private final int maxHp;
    private final int attack;
    private final int defense;
    private final int speed;
    private final String equipment;
    private final String characterClass;
    private final String temporaryBuff;

    /**
     * Constructor privado usado por el Builder.
     * Evita el anti-patrón Telescoping Constructor.
     */
    private Character(CharacterBuilder builder) {
        this.name = builder.name;
        this.maxHp = builder.maxHp;
        this.currentHp = builder.maxHp;
        this.attack = builder.attack;
        this.defense = builder.defense;
        this.speed = builder.speed;
        this.equipment = builder.equipment;
        this.characterClass = builder.characterClass;
        this.temporaryBuff = builder.temporaryBuff;
    }

    public String getName() {
        return name;
    }

    public int getCurrentHp() {
        return currentHp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getSpeed() {
        return speed;
    }

    public String getEquipment() {
        return equipment;
    }

    public String getCharacterClass() {
        return characterClass;
    }

    public String getTemporaryBuff() {
        return temporaryBuff;
    }

    public void takeDamage(int damage) {
        this.currentHp = Math.max(0, currentHp - damage);
    }

    public boolean isAlive() {
        return currentHp > 0;
    }

    public double getHpPercentage() {
        return maxHp > 0 ? (double) currentHp / maxHp * 100 : 0;
    }

    // Permite modificar la vida actual (para undo de comandos)
    public void setCurrentHp(int hp) {
        this.currentHp = Math.max(0, Math.min(hp, maxHp));
    }

    /**
     * Patrón Builder: Permite construir objetos Character paso a paso.
     * Resuelve el problema del constructor telescópico al proporcionar:
     * - Nombres claros para cada parámetro (métodos withXxx)
     * - Valores por defecto preconfigurados
     * - Construcción legible y flexible
     */
    public static class CharacterBuilder {
        // Valores por defecto
        private String name = "Sin nombre";
        private int maxHp = 100;
        private int attack = 10;
        private int defense = 10;
        private int speed = 10;
        private String equipment = null;
        private String characterClass = "Aventurero";
        private String temporaryBuff = null;

        public CharacterBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public CharacterBuilder withHp(int maxHp) {
            this.maxHp = maxHp;
            return this;
        }

        public CharacterBuilder withAttack(int attack) {
            this.attack = attack;
            return this;
        }

        public CharacterBuilder withDefense(int defense) {
            this.defense = defense;
            return this;
        }

        public CharacterBuilder withSpeed(int speed) {
            this.speed = speed;
            return this;
        }

        public CharacterBuilder withEquipment(String equipment) {
            this.equipment = equipment;
            return this;
        }

        public CharacterBuilder withClass(String characterClass) {
            this.characterClass = characterClass;
            return this;
        }

        public CharacterBuilder withTemporaryBuff(String temporaryBuff) {
            this.temporaryBuff = temporaryBuff;
            return this;
        }

        /**
         * Construye el objeto Character con los valores configurados.
         */
        public Character build() {
            return new Character(this);
        }
    }
}
