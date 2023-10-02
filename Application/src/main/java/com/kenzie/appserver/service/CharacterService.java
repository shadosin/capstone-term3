package com.kenzie.appserver.service;
import com.kenzie.appserver.repositories.CharacterRepository;
import com.kenzie.appserver.repositories.model.CharacterRecord;
import com.kenzie.appserver.service.model.Character;
import org.springframework.stereotype.Service;

@Service
public class CharacterService {
    private CharacterRepository characterRepository;

    public CharacterService(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    public Character findByName(String character_name) {

        return characterRepository
                .findById(character_name)
                .map(character -> new Character(character.getCharacter_name(), character.getStrength(),
                                                character.getDexterity(), character.getSocial(),
                                                character.getMagic(), character.getMana(),
                                                character.getHealthPoints()))
                .orElse(null);
    }
    public Character addNewCharacter(Character character) {

        CharacterRecord characterRecord = new CharacterRecord();
        characterRecord.setCharacter_name(character.getCharacter_name());
        characterRecord.setDexterity(character.getDexterity());
        characterRecord.setMagic(character.getMagic());
        characterRecord.setMana(character.getMana());
        characterRecord.setSocial(character.getSocial());
        characterRecord.setStrength(character.getStrength());
        characterRepository.save(characterRecord);
        return character;
    }
    public void deleteCharacter(String character_name){
        characterRepository.deleteById(character_name);
    }
}
