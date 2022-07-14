<template> 
  <div v-show="showModal === true" class="w3-modal" style="display: block">
    <div class="w3-modal-content modal-window" style="width: 90%">
      <button @click="exit" style="position: fixed"
              class="w3-btn w3-circle w3-display-topright w3-card" id="closeModal">X</button>

      <div>
        <div class="w3-container heading">
          <h3>Add or Edit a Character</h3>
        </div>

        <div class="w3-container">
          <div>
            <label class="w3-right-align margin-right-10 margin-top-15">Character's Name: </label>
            <input type="text" class="w3-left-align w3-card" id="character-input" placeholder="Type Name" v-model="state.character.characterName" @keyup.enter="submit">
          </div>
        </div>

        <div class = "w3-container error" v-show="state.errorMessage !== ''">
          {{ state.errorMessage }}
        </div>

        <div class="w3-container">
          <button class="w3-button submit-button" @click="submit">Save</button>
          <button class="w3-button cancel-button" @click="exit">Cancel</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { reactive, watch } from 'vue';
  import { useToast } from "primevue/usetoast";
  import { characterRepository } from '@/store/repository/characterRepository.js';

  export default {
    name: 'CharacterAddEdit',

    emits: ['close-character-add-edit'],

    props: ['showModal', 'characterId'],

    setup(props, context) {
        const state = reactive({
          characters: [],
          character: {
            characterName: ''
          },
          errorMessage: '', 
          successMessage: '', 
          addMode: true, 
          selectedCharacterId: 0
        })

        const toast = useToast();

        const {
          getActiveCharacters, 
          getCharacterByName, 
          getCharacterById, 
          postCharacter, 
          putCharacter
        } = characterRepository();

        watch(props, async function() {
          if (props.showModal) {
            state.errorMessage = '';

            if (props.characterId !== 0) {
              state.character = await getCharacterById(props.characterId);
              state.addMode = false;

            } else {
              state.character.characterName = '';

              state.addMode = true;
            }
          }
        });

        async function validate() {
          state.errorMessage = '';
          state.successMessage = '';

          if (state.character.characterName === '') {
            state.errorMessage = 'Sorry, there was an error. Try again.';
          }
        }

        async function submit() {
          await validate();
          
          if (state.errorMessage === '') {

            if (state.addMode) {
              let newId = await postCharacter(state.character);

              if (newId === 0) {
                state.errorMessage = 'That didn\'t work. Try someone else.';
              } else if (newId === -1) {
                state.errorMessage = 'Sorry, there was an error while adding.';
              } else {
                state.character.characterName = '';

                toast.add({severity:'success', summary: 'Successful Add', detail:'Character added', life: 4000});

                context.emit('close-character-add-edit', true);
              }   
            }

            else {
              let updateId = await putCharacter(state.character);

              if (updateId === 0) {
                state.errorMessage = 'It doesn\'t look like you changed anything.';
              }
              else if (updateId === -1) {
                state.errorMessage = 'Sorry, there was an error while updating.';
              }
              else if(updateId === -2) {
                state.errorMessage = 'This character already exists.';
              }
              else {
                state.character.characterName = '';

                toast.add({severity:'success', summary: 'Successful Update', detail:'Character updated', life: 4000});

                context.emit('close-character-add-edit', true);
              }
            }
          }
        }

      function exit() {
        context.emit('close-character-add-edit', false);
      }

      return {
        state, 
        getActiveCharacters, 
        getCharacterByName,
        submit, 
        validate, 
        exit, 
      }
    }
  }
</script>

<style scoped>
  input {
    width: 10rem;
  }

  .w3-modal-content {
    border-radius: 8px;
    border: 2px solid var(--light-gray);
  }

  #closeModal {
    top: 20px;
    right: 30px;
  }

  .heading {
    background-color: var(--blue);
    color: white;
    padding-top: 1px;
    padding-bottom: 1px;
    border-top-left-radius: 8px;
    border-top-right-radius: 8px;
    margin-bottom: 10px;
  }

  #character-input {
    margin-top: 15px;
    margin-bottom: 15px;
    border-radius: 8px;
    border: 2px solid var(--light-gray);
    padding-left: 10px;
  }

  .cancel-button {
    background-color: var(--red);
    margin: 8px;
    border-radius: 8px;
    color: white;
    margin-bottom: 20px;
  }

  .submit-button {
    background-color: var(--green);
    border-radius: 8px;
    margin: 8px;
    color: white;
    margin-bottom: 20px;
  }

  .error {
    width: 100%;
    padding: 4px;
    background-color: var(--red);
    color: white;
    margin-top: 10px;
    margin-bottom: 10px;
  }

</style>