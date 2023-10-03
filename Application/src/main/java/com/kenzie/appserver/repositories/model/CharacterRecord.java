package com.kenzie.appserver.repositories.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.Objects;

@DynamoDBTable(tableName = "Character")
public class CharacterRecord {

    private String character_name;
    private int strength;
    private int dexterity;
    private int social;
    private int magic;
    private int mana;
    private int healthPoints;



    @DynamoDBHashKey(attributeName = "character_name")
    public String getCharacter_name() {
        return this.character_name;
    }
    @DynamoDBAttribute(attributeName = "strength")
    public int getStrength() {
        return this.strength;
    }
    @DynamoDBAttribute(attributeName = "dexterity")
    public int getDexterity() {
        return this.dexterity;
    }
    @DynamoDBAttribute(attributeName = "social")
    public int getSocial() {
        return this.social;
    }
    @DynamoDBAttribute(attributeName = "magic")
    public int getMagic() {
        return this.magic;
    }
    @DynamoDBAttribute(attributeName = "mana")
    public int getMana() {
        return this.mana;
    }

    public void setCharacter_name(String character_name) {
        this.character_name = character_name;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void setSocial(int social) {
        this.social = social;
    }

    public void setMagic(int magic) {
        this.magic = magic;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CharacterRecord characterRecord = (CharacterRecord) o;
        return Objects.equals(character_name, characterRecord.character_name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(character_name);
    }


}
