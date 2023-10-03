package com.kenzie.appserver.service;
import com.kenzie.appserver.controller.model.CharacterResponse;
import com.kenzie.appserver.repositories.CharacterRepository;
import com.kenzie.appserver.repositories.model.CharacterRecord;
import com.kenzie.appserver.service.model.Character;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<Character> findAllCharacters(){
        List<Character> characters = new ArrayList<>();

        Iterable<CharacterRecord> characterIterator = characterRepository.findAll();
        for(CharacterRecord record: characterIterator){
            characters.add(new Character(record.getCharacter_name(),
                    record.getStrength(), record.getDexterity(), record.getSocial(), record.getMagic(),
                    record.getMana(), record.getHealthPoints()));
        }
        return characters;
    }
    public Character addNewCharacter(Character character) {

        CharacterRecord characterRecord = new CharacterRecord();
        characterRecord.setCharacter_name(character.getCharacter_name());
        characterRecord.setDexterity(character.getDexterity());
        characterRecord.setMagic(character.getMagic());
        characterRecord.setMana(character.getMana());
        characterRecord.setSocial(character.getSocial());
        characterRecord.setStrength(character.getStrength());
        characterRecord.setHealthPoints(character.getHealthPoints());
        characterRepository.save(characterRecord);
        return character;
    }

    public void updateCharacter(Character character){
        if(characterRepository.existsById(character.getCharacter_name())){
            CharacterRecord characterRecord = new CharacterRecord();
            characterRecord.setCharacter_name(character.getCharacter_name());
            characterRecord.setDexterity(character.getDexterity());
            characterRecord.setStrength(character.getStrength());
            characterRecord.setHealthPoints(character.getHealthPoints());
            characterRecord.setMagic(character.getMagic());
            characterRecord.setMana(character.getMana());
            characterRecord.setSocial(character.getSocial());
            characterRepository.save(characterRecord);

        }
    }
    public void deleteCharacter(String character_name){
        characterRepository.deleteById(character_name);
    }
}
