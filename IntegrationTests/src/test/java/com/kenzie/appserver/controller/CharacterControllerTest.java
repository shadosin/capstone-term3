package com.kenzie.appserver.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.kenzie.appserver.IntegrationTest;
import com.kenzie.appserver.controller.model.CharacterCreateRequest;
import com.kenzie.appserver.service.CharacterService;
import net.andreinc.mockneat.MockNeat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@IntegrationTest
public class CharacterControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    CharacterService characterService;
    private final MockNeat mockNeat = MockNeat.threadLocal();
    private final ObjectMapper mapper = new ObjectMapper();
    private final String CHARACTER_NAME = "NAME";
//    @Test
//    void getCharacter_returnsCharacter(){
//     mvc.perform()

//    @Test
//    public void getById_Exists() throws Exception {
//        String id = UUID.randomUUID().toString();
//        String name = mockNeat.strings().valStr();
//
//        Character example = new CharacterService();
//        Character persistedExample = characterService.addNewCharacter
//                (example));
//        mvc.perform(get("/example/{id}", persistedExample.getId())
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("id")
//                        .value(is(id)))
//                .andExpect(jsonPath("name")
//                        .value(is(name)))
//                .andExpect(status().isOk());
//    }

    @Test
    public void createCharacter_CreateSuccessful() throws Exception {
        String name = mockNeat.strings().valStr();

        CharacterCreateRequest characterCreateRequest = new CharacterCreateRequest();
        characterCreateRequest.setCharacter_name(mockNeat.toString());
        characterCreateRequest.setDexterity(mockNeat.ints().range(1,100).get());
        characterCreateRequest.setHealthPoints(mockNeat.ints().range(1,100).get());
        characterCreateRequest.setMana(mockNeat.ints().range(1,100).get());
        characterCreateRequest.setMagic(mockNeat.ints().range(1,100).get());
        characterCreateRequest.setStrength(mockNeat.ints().range(1,100).get());
        characterCreateRequest.setSocial(mockNeat.ints().range(1,100).get());
        mapper.registerModule(new JavaTimeModule());

        mvc.perform(post("/example")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(characterCreateRequest)))
                .andExpect(jsonPath("id")
                        .exists())
                .andExpect(jsonPath("name")
                        .value(is(name)))
                .andExpect(status().isCreated());
    }
}
