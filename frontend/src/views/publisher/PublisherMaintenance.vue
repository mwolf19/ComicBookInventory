<template>
  <div>
    <div class="w3-center">
      <h1><strong>Publisher Maintenance</strong></h1>
    </div>

    <div class="w3-container align-h-center align-v-center margin-top-10">
      <div class="w3-container w3-red" v-show="state.errorMessage !== ''">
        {{ state.errorMessage }}
      </div>
    </div>

    <div>
      <button class="w3-btn margin-top-15" id="addNew-btn" @click="addNew">Add New Publisher</button>
    </div>

    <div class="w3-container table">
      <table class="w3-table w3-bordered w3-card">
        <tr style="background-color:var(--blue); color:white;">
          <td style="padding-left:45px; font-size:20px;"><strong>Publisher</strong></td>
          <td style="font-size:20px; text-align:center;"><strong>Edit</strong></td>
          <td style="font-size:20px; text-align:center"><strong>Delete</strong></td>
        </tr>

        <tr v-for="(publisher, id) in state.publishers" :key="id">

          <td class="w3-left-align cell-v-center" style="width: 5%; padding-left:45px;">
            {{ publisher.publisherName }}
          </td>

          <td class="w3-center cell-center" style="width: 5%">
            <button class="w3-btn" id="edit-btn" @click="editSelected(publisher.publisherId)"></button>
          </td>

          <td class="w3-center cell-center" style="width: 5%">
            <button class="w3-btn" id="delete-btn" @click="deleteSelected(publisher.publisherId)"></button>
          </td>
        </tr>
      </table>
    </div>

    <PublisherAddEdit :showModal="state.showAddEdit" :publisherId="state.selectedPublisherId"
                      @close-publisher-add-edit="closeAddEdit"/>
  </div>
</template>

<script>
  import { reactive, onMounted } from 'vue';
  import { useToast } from "primevue/usetoast";
  import { publisherRepository } from '@/store/repository/publisherRepository.js'

  import PublisherAddEdit from '@/views/publisher/PublisherAddEdit.vue';

  export default {
    name: 'PublisherMaintenance',

    components: {
      PublisherAddEdit
    },

    setup() {
      const state = reactive({
        publishers: [],
        showAddEdit: false,
        selectedPublisherId: 0,
        errorMessage: ''
      })

      const toast = useToast();

      const {
        getActivePublishers,
        deletePublisher
      } = publisherRepository();

      onMounted(async () => {
        state.publishers = await getActivePublishers();
      });

      function addNew() {
        state.selectedPublisherId = 0;

        state.showAddEdit = true;
      }

      function editSelected(id) {
        state.selectedPublisherId = id;

        state.showAddEdit = true;
      }

      async function deleteSelected(id) {
        let success = await deletePublisher(id);

        if (success === -1) {
          state.errorMessage = 'There was an error deleting the publisher';
        } else {
          toast.add({severity:'success', summary: 'Successful Delete', detail:'Publisher deleted', life: 4000});
          
          state.publishers = await getActivePublishers();
        }
      }

      async function closeAddEdit(result) {
        state.showAddEdit = false;

        if (result === true) {
          state.publishers = await getActivePublishers();
        }
      }

      return {
        state,
        getActivePublishers,
        addNew,
        editSelected,
        deleteSelected,
        closeAddEdit
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