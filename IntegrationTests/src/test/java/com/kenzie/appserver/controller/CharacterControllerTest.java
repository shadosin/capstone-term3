package com.kenzie.appserver.controller;

import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.CharacterCreateRequest;
import com.kenzie.appserver.service.CharacterService;
import com.kenzie.appserver.service.model.Character;
import com.kenzie.appserver.service.model.Example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@IntegrationTest
class CharacterControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    CharacterService characterService;

    private final MockNeat mockNeat = MockNeat.threadLocal();

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void getCharacter_returnsCharacter() throws Exception {
        //GIVEN
        String character_name = mockNeat.strings().valStr();

        Character character = new Character(
                character_name,
                2,
                3,
                4,
                5,
                6,
                7
        );
        Character existingCharacter = characterService.addNewCharacter(character);

        //WHEN
        mvc.perform(get("/character/{character_name}",existingCharacter.getCharacter_name())
                        .accept(MediaType.APPLICATION_JSON))

        //THEN
                .andExpect(jsonPath("character_name")
                        .value(is(character_name)))
                .andExpect(jsonPath("strength")
                        .value(is(2)))
                .andExpect(jsonPath("dexterity")
                        .value(is(3)))
                .andExpect(jsonPath("social")
                        .value(is(4)))
                .andExpect(jsonPath("magic")
                        .value(is(5)))
                .andExpect(jsonPath("mana")
                        .value(is(6)))
                .andExpect(jsonPath("healthPoints")
                        .value(is(7)))
                .andExpect(status().isOk());
    }
    @Test
    public void getCharacter_characterNotFound() throws Exception{
        //GIVEN
        String character_name = mockNeat.strings().valStr();

        mvc.perform(get("/character", character_name)
                        .accept(MediaType.APPLICATION_JSON))
        //WHEN

        // THEN
                        .andExpect(status().isNoContent());
    }
    @Test
    public void addNewCharacter_characterAdded() throws Exception {
        //GIVEN
        String character_name = mockNeat.strings().valStr();
        Character newCharacter = new Character(
                character_name,
                2,
                3,
                4,
                5,
                6,
                7
        );

        CharacterCreateRequest request = new CharacterCreateRequest();
        request.setCharacter_name(mockNeat.strings().valStr());
        request.setStrength(1);
        request.setDexterity(2);
        request.setSocial(3);
        request.setMagic(4);
        request.setMana(5);
        request.setHealthPoints(6);
        //WHEN
        mvc.perform(get("/character", character_name)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(request)))

        //THEN
                .andExpect(jsonPath("character_name")
                        .value(request.getCharacter_name()))
                .andExpect(status().isCreated());
    }
}