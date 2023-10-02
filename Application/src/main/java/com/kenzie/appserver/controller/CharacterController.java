package com.kenzie.appserver.controller;

import com.kenzie.appserver.controller.model.CharacterResponse;
import com.kenzie.appserver.controller.model.ExampleCreateRequest;
import com.kenzie.appserver.controller.model.ExampleResponse;
import com.kenzie.appserver.service.CharacterService;
import com.kenzie.appserver.service.ExampleService;

import com.kenzie.appserver.service.model.Character;
import com.kenzie.appserver.service.model.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static java.util.UUID.randomUUID;

@RestController
@RequestMapping("/character")
public class CharacterController {

    private CharacterService characterService;

    CharacterController(CharacterService characterService) {
        this.characterService = characterService;
    }

    @GetMapping("/{character_name}")
    public ResponseEntity<CharacterResponse> get(@PathVariable("character_name") String character_name) {

        Character character = characterService.findByName(character_name);
        if (character == null) {
            return ResponseEntity.notFound().build();
        }

        CharacterResponse characterResponse = new CharacterResponse();
        characterResponse.setCharacter_name(character.getCharacter_name());
        characterResponse.setDexterity(character.getDexterity());
        characterResponse.setStrength(character.getStrength());
        characterResponse.setHealthPoints(character.getHealthPoints());
        characterResponse.setMagic(character.getMagic());
        characterResponse.setMana(character.getMana());
        characterResponse.setSocial(character.getSocial());
        return ResponseEntity.ok(characterResponse);
    }

    @PostMapping
    public ResponseEntity<CharacterResponse> addNewConcert(@RequestBody  exampleCreateRequest) {
        Example example = new Example(randomUUID().toString(),
                exampleCreateRequest.getName());
        exampleService.addNewExample(example);

        ExampleResponse exampleResponse = new ExampleResponse();
        exampleResponse.setId(example.getId());
        exampleResponse.setName(example.getName());

        return ResponseEntity.created(URI.create("/example/" + exampleResponse.getId())).body(exampleResponse);
    }
    @DeleteMapping("/{character_name}")
    public ResponseEntity deleteCharacterByName(@PathVariable("character_name") String character_name){
        characterService.deleteCharacter(character_name);
        return ResponseEntity.noContent().build()
    }
}
