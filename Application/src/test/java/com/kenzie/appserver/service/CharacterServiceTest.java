package com.kenzie.appserver.service;

import com.kenzie.appserver.repositories.CharacterRepository;
import com.kenzie.appserver.repositories.model.CharacterRecord;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.kenzie.appserver.service.model.Character;
import org.mockito.ArgumentCaptor;
import org.testcontainers.shaded.com.google.common.base.Verify;

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

}
