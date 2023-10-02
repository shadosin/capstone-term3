package com.kenzie.appserver.controller.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CharacterCreateRequest {

    private String character_name;
    private int strength;
    private int dexterity;
    private int social;
    private int magic;
    private int mana;
    private int healthPoints;
    @JsonProperty("character_name")
    public String getCharacter_name() {
        return character_name;
    }

    public void setCharacter_name(String character_name) {
        this.character_name = character_name;
    }
    @JsonProperty("strength")
    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
    @JsonProperty("dexterity")
    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }
    @JsonProperty("social")
    public int getSocial() {
        return social;
    }

    public void setSocial(int social) {
        this.social = social;
    }
    @JsonProperty("magic")
    public int getMagic() {
        return magic;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }
    @JsonProperty("mana")
    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }
    @JsonProperty("healthPoints")
    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }
}