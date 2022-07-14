<template>
  <div v-show="showModal === true" class="w3-modal" style="display: block">
    <div class="w3-modal-content" style="width: 90%">
      <button @click="closeMe" style="position: fixed"
              class="w3-btn w3-circle w3-display-topright w3-card" id="closeModal">X</button>
      <div>
        <div class="w3-center heading">
          <h2>Add or Edit a Publisher</h2>
        </div>
      
        <div class="w3-container">
          <div class="w3-row align-h-center align-v-center">
            <label class="w3-right-align margin-right-10 margin-top-15">Publisher Name</label>

            <input type="text" class="w3-left-align w3-card" id="publisher-input" v-model="state.publisher.publisherName" @keyup.enter="submit">
          </div>

          <div class="w3-row align-h-center align-v-center margin-top-10">
            <div class="w3-container error" v-show="state.errorMessage !== ''">
              {{ state.errorMessage }}
            </div>
          </div>

          <div class="w3-row align-h-center align-v-center margin-top-10">
            <button class="w3-button submit-button" @click="submit">Submit</button>
            <button class="w3-button cancel-button" @click="closeMe">Cancel</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { reactive, watch } from 'vue';
  import { useToast } from "primevue/usetoast";
  import { publisherRepository } from '@/store/repository/publisherRepository.js'

  export default {
    name: 'PublisherAddEdit',

    emits: ['close-publisher-add-edit'],

    props: ['showModal', 'publisherId'],

    setup(props, context) {
      const state = reactive({
        publisher: {
          publisherName: ''
        },
        editMode: false,
        errorMessage: ''
      });

      const toast = useToast();

      const {
        getPublisherById,
        postPublisher,
        putPublisher
      } = publisherRepository();

      watch(props, async function() {
        if (props.showModal) {
          state.errorMessage = '';

          if (props.publisherId !== 0) {
            state.publisher = await getPublisherById(props.publisherId);

            state.editMode = true;

          } else {
            state.publisher.publisherName = '';

            state.editMode = false;
          }
        }
      });

      async function validate() {
        state.errorMessage = '';

        if (state.publisher.publisherName === '') {
          state.errorMessage = 'Please enter a publisher name';
        }
      }

      async function submit() {
        await validate();

        if (state.errorMessage === '') {
          let returnId;

          if (state.editMode){
            returnId = await putPublisher(state.publisher);

          } else {
            returnId = await postPublisher(state.publisher);
          }
          if (returnId === 0) { //Publisher already exists
            state.errorMessage = 'This publisher already exists in the system';

          } else if (returnId === -1) { //Error in API
            state.errorMessage = 'An error occured trying to add the publisher to the system';

          } else { //Success

            if (state.editMode){
              toast.add({severity:'success', summary: 'Successful Update', detail:'Publisher updated', life: 4000});
            } else {
              toast.add({severity:'success', summary: 'Successful Add', detail:'Publisher added', life: 4000});
            }

            context.emit('close-publisher-add-edit', true);
          }
        }
      }

      function closeMe() {
        context.emit('close-publisher-add-edit', false);
      }

      return {
        state,
        postPublisher,
        validate,
        submit,
        closeMe
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

  #publisher-input {
    margin-top: 15px;
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
  }

</style>