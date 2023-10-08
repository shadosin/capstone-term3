package com.kenzie.appserver.service;

import com.kenzie.appserver.controller.model.CharacterResponse;
import com.kenzie.appserver.repositories.CharacterRepository;
import com.kenzie.appserver.repositories.model.CharacterRecord;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.kenzie.appserver.service.model.Character;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CharacterServiceTest {
    private CharacterRepository characterRepository;
    private  CharacterService characterService;

    @BeforeEach
    void setup(){
        characterRepository = mock(CharacterRepository.class);
        characterService = new CharacterService(characterRepository);
    }

    @Test
    void add_newCharacter(){
        Character testCharacter = new Character("sara", 50,
                40,30,20,10,60);
        ArgumentCaptor<CharacterRecord> captor = new ArgumentCaptor<>();

        Character resultCharacter = characterService.addNewCharacter(testCharacter);
        verify(characterRepository).save(captor.capture());
        CharacterRecord savedRecord = captor.getValue();

        Assert.assertEquals(testCharacter, resultCharacter);
        Assert.assertEquals(testCharacter.getCharacter_name(), savedRecord.getCharacter_name());
        Assert.assertEquals(testCharacter.getStrength(), savedRecord.getStrength());
        Assert.assertEquals(testCharacter.getDexterity(), savedRecord.getDexterity());
        Assert.assertEquals(testCharacter.getSocial(), savedRecord.getSocial());
        Assert.assertEquals(testCharacter.getMagic(), savedRecord.getMagic());
        Assert.assertEquals(testCharacter.getMana(), savedRecord.getMana());
        Assert.assertEquals(testCharacter.getHealthPoints(), savedRecord.getHealthPoints());

    }
//    @Test
//    void addNewCharacter_inputNull_throwsException(){
//
//    }



    @Test
    void updateCharacter(){
        Character testCharacter = new Character("sara", 50,
                40,30,20,10,60);
        ArgumentCaptor<CharacterRecord> captor = new ArgumentCaptor<>();
        when(characterRepository.existsById("sara")).thenReturn(true);

        characterService.updateCharacter(testCharacter);
        verify(characterRepository).save(captor.capture());
        verify(characterRepository).existsById("sara");
        CharacterRecord savedRecord = captor.getValue();

        Assert.assertEquals(testCharacter.getCharacter_name(), savedRecord.getCharacter_name());
        Assert.assertEquals(testCharacter.getStrength(), savedRecord.getStrength());
        Assert.assertEquals(testCharacter.getDexterity(), savedRecord.getDexterity());
        Assert.assertEquals(testCharacter.getSocial(), savedRecord.getSocial());
        Assert.assertEquals(testCharacter.getMagic(), savedRecord.getMagic());
        Assert.assertEquals(testCharacter.getMana(), savedRecord.getMana());
        Assert.assertEquals(testCharacter.getHealthPoints(), savedRecord.getHealthPoints());

    }

    @Test
    void updateCharacter_doesNotExist() {
        Character testCharacter = new Character("sara", 50,
                40, 30, 20, 10, 60);

        when(characterRepository.existsById("sara")).thenReturn(false);

        characterService.updateCharacter(testCharacter);

        verify(characterRepository).existsById("sara");
        verify(characterRepository, times(0)).save(any());

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
//        verify(characterService.deleteCharacter(existingCharacter.getCharacter_name(),times(1));
        assertFalse(characterRepository.existsById(existingCharacter.getCharacter_name()));
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
            assertEquals(character.getCharacter_name(),existingCharacter.getCharacter_name());
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
            assertTrue(characterRecords.size() == 1);
            assertEquals(listOfCharacters.size(), characterRecords.size());


        }
    }

