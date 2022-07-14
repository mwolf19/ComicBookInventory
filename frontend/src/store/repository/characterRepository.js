import { ref } from 'vue';

export function characterRepository() {
    const characters = ref([]);
    const character = ref({});
    const characterId = ref(0);

    async function getActiveCharacters() {
        const url = 'http://localhost:8080/hero/listAllActive';
        
        characters.value = [];
        
        await fetch(url)
        .then(response => response.json())
        .then(function (data) {
            characters.value = data;
        })
    
        return characters;
    }

    async function getCharacterByName(name) {
        const url = 'http://localhost:8080/hero/findByCharacterName/' + name;

        characterId.value = 0;

        await fetch(url)
        .then(response => response.json())
        .then(function (data) {
            characterId.value = data;
        })

        return characterId.value;
    }

    async function getCharacterById(id) {
        const url = 'http://localhost:8080/hero/listByCharacterId/' + id;

        character.value = {};

        await fetch(url)
        .then(response => response.json())
        .then(function (data) {
            character.value = data;
        })

        return character.value;
    }

    async function postCharacter(character) {
        const url = 'http://localhost:8080/hero/saveCharacter';
    
        characterId.value = 0;
    
        const options = {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(character)
        };
    
        await fetch(url, options)
        .then(response => response.json())
        .then(function (data) {
          characterId.value = data;
        })
    
        return characterId.value;
      }

    async function putCharacter(character) {
        const url = 'http://localhost:8080/hero/updateCharacter';

        characterId.value = 0;

        const options = {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(character)
        };

        await fetch(url, options) 
        .then(response => response.json())
        .then(function (data) {
            characterId.value = data;
        })

        return characterId.value;
    }

    async function deleteCharacter(id) {
        const url = 'http://localhost:8080/hero/deleteCharacter/' + id;

        characterId.value = 0;

        const options = {
            method: "PUT", 
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(character)
        };

        await fetch(url, options) 
        .then(response => response.json())
        .then(function (data) {
            characterId.value = data;
        })

        return characterId.value;
        
    }

    return {
        getActiveCharacters,
        getCharacterByName, 
        getCharacterById, 
        postCharacter, 
        putCharacter, 
        deleteCharacter
    }
}