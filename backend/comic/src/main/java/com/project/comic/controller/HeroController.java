package com.project.comic.controller;

import com.project.comic.model.Hero;
import com.project.comic.repository.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/hero")
public class HeroController {
    @Autowired
    private HeroRepository heroRepository;

    public HeroController(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }


    @GetMapping(path="/listAllActive", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public List<Hero> findByIsActive() {
        return heroRepository.findByIsActive(true);
    }

    @GetMapping(path="/listByCharacterId/{id}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public Hero findByCharacterId(@PathVariable("id") int heroId) { return heroRepository.findByCharacterId(heroId); }

    @GetMapping(path="/findByCharacterName/{characterName}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public int findByCharacterName(@PathVariable("characterName") String characterName) {
        try {
            int characterId = 0;

            Hero hero = heroRepository.findByCharacterNameAndIsActive(characterName, true);

            if (hero != null) {
                characterId = hero.getCharacterId();
            }

            return characterId;
        } catch (Exception e) {
            return -1;
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping(path="/saveCharacter", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public int saveCharacter(@RequestBody Hero heroModel) {
        try {
            Hero existingCheck = heroRepository.findByCharacterNameAndIsActive(heroModel.getCharacterName(), true);

            int newId = 0;

            if (existingCheck == null || !heroModel.getCharacterName().equalsIgnoreCase(existingCheck.getCharacterName())) {
                if (!heroModel.getCharacterName().isBlank()) {
                    heroModel.setIsActive(true);
                    newId = heroRepository.save(heroModel).getCharacterId();
                }
            }

            return newId;
        } catch (Exception e) {
            return -1;
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping(path="/updateCharacter", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public int updateCharacter(@RequestBody Hero heroModel) {
        try {
            Hero idCheck = heroRepository.findByCharacterIdAndIsActive(heroModel.getCharacterId(), true);
            int newId = 0;

            if (idCheck != null && !heroModel.getCharacterName().isBlank()) {
                Hero current = heroRepository.findByCharacterId(heroModel.getCharacterId());
                if (current.getCharacterName() != heroModel.getCharacterName()) {
                    for (Hero hero : heroRepository.findByIsActive(true)) {
                        if (hero.getCharacterName().equalsIgnoreCase(heroModel.getCharacterName())) {
                            if (hero.getCharacterName().equalsIgnoreCase(current.getCharacterName())) {
                                return 0;
                            }
                            return -2;
                        }
                    }
                    current.setCharacterName(heroModel.getCharacterName());
                    newId = heroRepository.save(current).getCharacterId();
                }
            }

            return newId;
        }
        catch (Exception e) {
            return -1;
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping(path="/deleteCharacter/{id}", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public int deleteCharacter(@PathVariable("id") int heroId) {
        try {
            Hero existingCheck = heroRepository.findByCharacterIdAndIsActive(heroId, true);

            int newId = 0;

            if (existingCheck != null) {
                Hero current = heroRepository.findByCharacterId(heroId);
                current.setIsActive(false);
                newId = heroRepository.save(current).getCharacterId();
            }

            return newId;
        } catch (Exception e) {
            return -1;
        }
    }
}