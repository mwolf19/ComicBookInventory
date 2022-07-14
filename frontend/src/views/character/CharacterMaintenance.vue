<template>
  <div>
    <div class="w3-center heading">
      <h1><strong>Character Maintenance</strong></h1>
    </div>

    <div class="w3-container align-h-center align-v-center margin-top-10">
      <div class="w3-container w3-red" v-show="state.errorMessage !== ''">
        {{ state.errorMessage }}
      </div>
    </div>

    <div>
      <button class="w3-btn margin-top-15" id="addNew-btn" @click="addNew">Add New Character</button>
    </div>

    <div class="w3-container table">
      <table class="w3-table w3-bordered w3-card">
        <tr style="background-color:var(--blue); color:white;">
          <td style="padding-left:45px; font-size:20px;"><strong>Character</strong></td>
          <td style="font-size:20px; text-align:center;"><strong>Edit</strong></td>
          <td style="font-size:20px; text-align:center"><strong>Delete</strong></td>
        </tr>

        <tr v-for="(character, id) in state.characters" :key="id">
          <td class="w3-left-align cell-v-center" style="width: 5%; padding-left:45px;">
            {{ character.characterName }}
          </td>

          <td class="w3-center cell-center" style="width: 5%">
            <button class="w3-btn" id="edit-btn" @click="editSelected(character.characterId)"></button>
          </td>

          <td class="w3-center cell-center" style="width: 5%">
            <button class="w3-btn" id="delete-btn" @click="deleteSelected(character.characterId)"></button>
          </td>
        </tr>
      </table>
    </div>

    <CharacterAddEdit :showModal="state.addEditMode" :characterId="state.selectedCharacterId" 
        @close-character-add-edit="closeModal"/>
  </div>
</template>

<script>
  import { reactive, onMounted } from 'vue';
  import { useToast } from "primevue/usetoast";
  import { characterRepository } from '@/store/repository/characterRepository.js'

  import CharacterAddEdit from '@/views/character/CharacterAddEdit.vue';

  export default {
    name: 'CharacterMaintenance',

    components: {
      CharacterAddEdit
    },

    setup() {
      const state = reactive({
        characters: [],
        addEditMode: false,
        selectedCharacterId: 0,
        errorMessage: ''
      })

      const toast = useToast();

      const {
        getActiveCharacters,
        deleteCharacter
      } = characterRepository();

      onMounted(async () => {
        state.characters = await getActiveCharacters();
      });

      function addNew() {
        state.selectedCharacterId = 0;

        state.addEditMode = true;
      }

      function editSelected(id) {
        state.selectedCharacterId = id;

        state.addEditMode = true;
      }

      async function deleteSelected(id) {
        let success = await deleteCharacter(id);

        if (success === -1) {
          state.errorMessage = 'Sorry, there was an error deleting the character.';
        } else {
          toast.add({severity:'success', summary: 'Successful Delete', detail:'Character deleted', life: 4000});

          state.characters = await getActiveCharacters();
        }

      }

      async function closeModal(result) {
        state.addEditMode = false;

        if (result === true) {
          state.characters = await getActiveCharacters();
        }
      }

      return {
        state,
        getActiveCharacters,
        addNew,
        editSelected,
        deleteSelected,
        closeModal
      }
    }
  }
</script>

<style scoped>
 input {
    width: 10rem;
  }

  .table {
    width: 75%;
    transform: translate(16%, 2%);
  }

  #addNew-btn {
    background-color: var(--green);
    color: white;
    border-radius: 8px;
  }

  #edit-btn {
    background-image: url('../../assets/images/editIcon.png');
    background-repeat: no-repeat;
    height: 30px;
    width: 40px;
    background-size: 50%;
    border-radius: 8px;
    background-position: 55%;
    background-color: var(--yellow);
  }

  #delete-btn {
    background-image: url('../../assets/images/trashIcon.png');
    background-repeat: no-repeat;
    height: 30px;
    width: 40px;
    background-size: 50%;
    background-color: var(--red);
    border-radius: 8px;
    background-position: 55%;
  }
</style>