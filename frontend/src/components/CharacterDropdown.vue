<template>
  <div>
    <select class="w3-select w3-round-large w3-card" id="dropdown" style="width: 215px" v-model="state.selectedCharacter" @change="selectionChanged()">
      <option value="" disabled selected><em>Please choose a character</em></option>

      <option v-for="(character, id) in state.characterList" :key="id" :value="character.characterId">{{ character.characterName }}</option>
    </select>
  </div>
</template> 

<script>
  import { reactive, onMounted, watch } from 'vue';
  import { characterRepository } from '@/store/repository/characterRepository';
  
  export default {
    name: 'CharacterDropdown',
    
    emits: ['selected-character-changed'],

    props: ['selectedId'],

    setup(props, context) {
      onMounted(() => {
        loadCharacters();
      });

      const state = reactive({
        selectedCharacter: 0,
        characterList: []
      });

      watch(props, async function() {
        state.selectedCharacter = props.selectedId
      });

      const {
        getActiveCharacters
      } = characterRepository();

      async function loadCharacters() {
        state.characterList = await getActiveCharacters();
      }

      function selectionChanged() {
        context.emit('selected-character-changed', state.selectedCharacter);
      }

      return {
        state,
        loadCharacters,
        selectionChanged
      }
    }
  }
</script>

<style scoped>
  #dropdown {
    border: 1px solid var(--gray);
    background-color: var(--light-orange);
  }
</style>