package com.kenzie.appserver.service.model;

public class Character {
    private String character_name;
    private int strength;
    private int dexterity;
    private int social;
    private int magic;
    private int mana;
    private int healthPoints;
    public Character(String character_name, int strength, int dexterity, int social, int magic, int mana, int healthPoints){
        this.character_name = character_name;
        this.strength = strength;
        this.dexterity = dexterity;
        this.social = social;
        this.magic = magic;
        this.mana = mana;
        this.healthPoints = healthPoints;
    }

    public String getCharacter_name() {
        return character_name;
    }

    public int getStrength() {
        return strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public int getSocial() {
        return social;
    }

    public int getMagic() {
        return magic;
    }

    public int getMana() {
        return mana;
    }

    public int getHealthPoints() {
        return healthPoints;
    }
}
