package com.kenzie.appserver.repositories;

import com.kenzie.appserver.repositories.model.CharacterRecord;
//import com.kenzie.appserver.repositories.model.ExampleRecord;

import com.kenzie.appserver.service.model.Character;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface CharacterRepository extends CrudRepository<CharacterRecord, String> {

}
