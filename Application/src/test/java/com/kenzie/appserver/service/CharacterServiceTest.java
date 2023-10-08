package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.CharacterRepository;
import com.kenzie.appserver.repositories.model.CharacterRecord;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.kenzie.appserver.service.model.Character;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.testcontainers.shaded.com.google.common.base.Verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class CharacterServiceTest {

    private CharacterRepository characterRepository;
    @Mock
    private  CharacterService characterService;

    @BeforeEach
    void setup(){
        characterRepository = mock(CharacterRepository.class);
        characterService = new CharacterService(characterRepository);
    }

    @Test
    void deleteCharacter_characterExist_deletionCompleted(){
        //GIVEN
        CharacterRecord existingCharacter = new CharacterRecord();
        existingCharacter.setCharacter_name("Sara");
        existingCharacter.setStrength(1);
        existingCharacter.setDexterity(2);
        existingCharacter.setSocial(3);
        existingCharacter.setMagic(4);
        existingCharacter.setMana(5);
        existingCharacter.setHealthPoints(6);


        doNothing().when(characterRepository).deleteById(existingCharacter.getCharacter_name());
        //WHEN
        characterService.deleteCharacter(existingCharacter.getCharacter_name());

        //THEN
        Assertions.assertFalse(characterRepository.existsById(existingCharacter.getCharacter_name()));
    }
    @Test
    void findCharacterByID_IDExist_returnsCharacter(){
        //GIVEN
        CharacterRecord existingCharacter = new CharacterRecord();
        existingCharacter.setCharacter_name("Sara");
        existingCharacter.setStrength(1);
        existingCharacter.setDexterity(2);
        existingCharacter.setSocial(3);
        existingCharacter.setMagic(4);
        existingCharacter.setMana(5);
        existingCharacter.setHealthPoints(6);


        when(characterRepository.findById(existingCharacter.getCharacter_name()))
                .thenReturn(Optional.of(existingCharacter));
        //WHEN
        Character character = characterService.findByName(existingCharacter.getCharacter_name());

        //THEN
        Assertions.assertEquals(character.getCharacter_name(),existingCharacter.getCharacter_name());
    }

    @Test
    void findAllCharacters_validCharacterList_returnsList(){
        //GIVEN
        List<CharacterRecord> characterRecords = new ArrayList<>();
        CharacterRecord existingCharacter = new CharacterRecord();
        existingCharacter.setCharacter_name("Sara");
        existingCharacter.setStrength(1);
        existingCharacter.setDexterity(2);
        existingCharacter.setSocial(3);
        existingCharacter.setMagic(4);
        existingCharacter.setMana(5);
        existingCharacter.setHealthPoints(6);
        characterRecords.add(existingCharacter);
        when(characterRepository.findAll()).thenReturn(characterRecords);
        //WHEN

        List<Character> listOfCharacters = characterService.findAllCharacters();

        //THEN
        Assertions.assertTrue(characterRecords.size() == 1);
        Assertions.assertEquals(listOfCharacters.size(), characterRecords.size());


    }
    @Test
    void addNewCharacter_validCharacter_addSuccessful(){
        //GIVEN
        Character existingCharacter = new Character(
                "Sara",
                1,
                2,
                3,
                4,
                5,
                6
        );
        //WHEN
        Character character = characterService.addNewCharacter(existingCharacter);
        //THEN
        Assertions.assertEquals(character.getCharacter_name(),existingCharacter.getCharacter_name());
    }
    @Test
    void updateCharacter_characterExist_existingCharacterUpdated(){
        //GIVEN
        Character character = new Character(
                "Sara",
                2,
                3,
                4,
                5,
                6,
                7
        );

         when(characterRepository.existsById(character.getCharacter_name())).thenReturn(true);
        //WHEN
        characterService.updateCharacter(character);

        //THEN
        verify((characterRepository), times(2));

    }
}
