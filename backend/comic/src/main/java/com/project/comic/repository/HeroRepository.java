package com.project.comic.repository;

import com.project.comic.model.Hero;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeroRepository extends CrudRepository<Hero, Integer> {
    List<Hero> findByIsActive(Boolean isActive);
    Hero findByCharacterName(String characterName);
    Hero findByCharacterNameAndIsActive(String characterName, Boolean isActive);
    Hero findByCharacterId(Integer characterId);
    Hero findByCharacterIdAndIsActive(Integer characterId, Boolean isActive);
}