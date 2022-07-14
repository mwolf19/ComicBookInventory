<template>
  <div v-show="showModal === true" class="w3-modal" style="display: block">
    <div class="w3-modal-content modal-window" style=" min-height: 90%; width:70%; border-radius:8px;">
      <div class="w3-btn w3-circle w3-display-topright w3-card" id="close-btn" 
            @click="closeMe">X
      </div>

      <div class="w3-container w3-left-align w3-half margin">
        <div v-if="!state.issue.coverImage || state.issue.coverImage === ''">
          <img class="image-preview" src="../assets/download.jpg">
        </div>

        <div v-else>
          <img class="image" id="image-file" :src="state.issue.coverImage"/>
        </div>

        <div v-show="state.showDelete === true || state.editMode === true">
          <label for="imageURL"><b>Image URL </b></label>
          <input type="text" id="imageURL" v-model="state.issue.coverImage" >
        </div>
      </div>

      <div class="w3-container w3-right-align w3-rest">
        <div class="w3-bar align-h-center align-v-center" v-show="state.issue.isActive === false">
          <div class="w3-bar-item w3-right-align">
            <label class="w3-red"><b>This issue as been deleted</b></label>
          </div>
        </div>

        <div class="w3-bar align-h-center align-v-center">
          <div class="w3-bar-item w3-right-align" style="width: 135px; margin-top:15px">
            <label for="title"><b>Title</b></label>
          </div>

          <div class="w3-bar-item w3-left-align" style="width: 215px; margin-top:15px">
            <label v-show="state.editMode === false">{{ state.issue.issueName }}</label>

            <input class="w3-round-large w3-card" placeholder="Issue Name" style="width: 215px" v-model="state.issue.issueName"
                    id="issueName" v-show="state.editMode === true"/>
          </div>
        </div>

        <div class="w3-bar align-h-center align-v-center">
          <div class="w3-bar-item w3-right-align" style="width: 135px">
            <label for="issue number"><b>Issue Number</b></label>
          </div>

          <div class="w3-bar-item w3-left-align" style="width: 215px">
            <label v-show="state.editMode === false">{{ state.issue.issueNumber }}</label>

            <input class="w3-round-large w3-card-4" style="width: 215px" v-model="state.issue.issueNumber"
                    type="number" id="issueNumber" v-show="state.editMode === true"/>
          </div>
        </div>

        <div class="w3-bar align-h-center align-v-center">
          <div class="w3-bar-item w3-right-align" style="width: 135px">
            <label for="publisher"><b>Publisher</b></label>
          </div>

          <div class="w3-bar-item w3-left-align" style="width: 215px">
            <label v-show="state.editMode === false">{{ state.issue.publisherName }}</label>

            <PublisherDropdown :selectedId="state.issue.publisherId" @selected-publisher-changed="selectionChanged" 
                                v-show="state.editMode === true"/>
          </div>
        </div>

        <div class="w3-bar align-h-center align-v-center">
          <div class="w3-bar-item w3-right-align" style="width: 135px">
            <label for="coverPrice"><b>Cover Price</b></label>
          </div>

          <div class="w3-bar-item w3-left-align" style="width: 215px">
            <label v-show="state.editMode === false">{{ state.issue.coverPrice }}</label>
            <input class="w3-round-large w3-card-4" style="width: 215px" v-model="state.issue.coverPrice" 
                    type="number" id="coverPrice" v-show="state.editMode === true"/>
          </div>
        </div>

        <div class="w3-bar align-h-center">
          <div class="w3-bar-item w3-right-align" style="width: 135px">
            <label for="character tags"><b>Character Tags</b></label>
          </div>

          <div class="w3-bar-item w3-left-align" style="width: 215px">
            <div v-show="state.editMode === false">
              <div v-for="(character, id) in state.characterArray" :key="id">
                {{ character }}
              </div>
            </div>

            <CharacterDropdown class="w3-round-large w3-card" style="width: 215px" :selectedId="state.selectedCharacterTag"
                                @selected-character-changed="selectedCharacterChanged" v-show="state.editMode === true"/>
            
            <div v-show="state.editMode === true">
              <div class="margin" v-for="(character, id) in state.characterArray" :key="id">
                  {{ character }}

                  <button class="w3-btn w3-grey" style="float: right" @click="removeCharacterTag(id)">X</button>
              </div>
            </div>
          </div>
        </div>

        <div class="w3-bar align-h-center align-v-center">
          <div class="w3-bar-item w3-right-align w3-red" v-show="state.errorMessage !== ''">
            {{ state.errorMessage }}
          </div>
        </div>
        
        <p v-show="state.showControls === true">
          <button class="w3-btn margin-right-15" id="browse-edit-btn" @click="submit"
                  v-show="state.editMode === true">Save</button>
          <button class="w3-btn margin-right-15" style="float:left" id="delete-btn" @click="remove"
                  v-show="state.showDelete === true">Delete</button>
          <button class="w3-btn margin-right-15" id="browse-edit-btn" @click="edit"
                  v-show="state.editMode === false">Edit</button>
        </p>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, ref, watch } from "vue";
import { useToast } from "primevue/usetoast";
import { appGlobal } from "@/store/global/appGlobal.js";
import { issueRepository } from "@/store/repository/issueRepository.js";
import { characterRepository } from "@/store/repository/characterRepository.js";
import PublisherDropdown from "@/components/PublisherDropdown.vue";
import CharacterDropdown from "@/components/CharacterDropdown.vue";

export default {
  name: "Issue",
  props: ['showModal', 'issueId'],

  components: {
    PublisherDropdown,
    CharacterDropdown
  },

  emits: ['close-issue'],

  setup(props, context) {
    const state = reactive({
      issue: {
        issueName: issueRepository.issueName,
        issueNumber: issueRepository.issueNumber,
        publisherId: issueRepository.publisherId,
        coverPrice: issueRepository.coverPrice,
        characterTags: issueRepository.characterTags,
        userId: 0,
        coverImage: ''
      },
      selectedCharacterTag: 0,
      editMode: false,
      errorMessage: "",
      successMessage: "",
      showControls: false,
      showDelete: false,
      characterArray: []
    });

    const toast = useToast();

    const files = ref([])

    watch(props, async function() {
      if (props.showModal) {
        state.errorMessage = '';
        
        state.showControls = true;
        state.showDelete = false;

        if (props.issueId !== 0) {
          state.issue = await getIssueById(props.issueId);

          if (state.issue.userId !== appGlobal.user.userId) {
            state.showControls = false;
          }

          state.editMode = false;

          state.characterArray = state.issue.characterString.split(',');

        } else {
          clearIssue();
          state.editMode = true;
          state.showDelete = false;
        }
      }
    });

    const { 
      getIssueById, 
      postIssue,
      removeIssue
    } = issueRepository();

    const { 
      getCharacterById,
      getCharacterByName
    } = characterRepository();

    function clearIssue() {
      state.issue.issueId = 0;
      state.issue.issueName = '';
      state.issue.issueNumber = 0;
      state.issue.publisherId = 0;
      state.issue.coverPrice = 0.00;
      state.issue.characterTags = ',';
      state.issue.userId = 0;
      state.issue.characterString = '';
      state.issue.publisherName = '';
      state.issue.image = '';
      state.characterArray = [];
    }

    async function validate() {
      state.errorMessage = "";
      state.successMessage = "";

      if (state.issue.issueName === "") {
        state.errorMessage = "Please enter an issue name";

      } else if (state.issue.issueNumber === "") {
        state.errorMessage = "Please enter an issue number";

      } else if (state.issue.publisherId === 0) {
        state.errorMessage = "Please select a publisher";

      } else if (state.issue.coverPrice === "") {
        state.errorMessage = "Please enter the cover price";

      } else if (state.issue.characterTags.length === 1) {
        state.errorMessage = "Please enter a character tag";
      }
    }

    async function submit() {
      await validate();

      if (state.errorMessage === "") {
        state.issue.userId = appGlobal.user.userId;

        let newId = await postIssue(state.issue);

        if (newId === -1) {
          toast.add({severity:'error', summary: 'ERROR', detail:'Issue error', life: 4000});

        } else {
          toast.add({severity:'success', summary: 'Success', detail:'Issue success', life: 4000});

          context.emit('close-issue', true)
        }
      }
    }

    function edit() {
      state.editMode = true;
      state.showDelete = true;
    }

    async function remove() {
      let success = await removeIssue(state.issue);

      if (success === 1) {
        toast.add({severity:'success', summary: 'Successful Delete', detail:'Issue deleted', life: 4000});

        context.emit('close-issue', true);
      } else {
        toast.add({severity:'error', summary: 'ERROR', detail:' delete error', life: 4000});
      }
    }

    function selectionChanged(publisherId) {
      state.issue.publisherId = publisherId;
    }

    async function selectedCharacterChanged(characterId) {
      state.errorMessage = '';

      if (!state.issue.characterTags.includes(',' + characterId + ',')){
        state.issue.characterTags += characterId + ',';

        let character = await getCharacterById(characterId);

        state.characterArray.push(character.characterName);

        state.selectedCharacterTag = '';
      } else {
        state.errorMessage = 'Character already selected';
      }
    }

    async function removeCharacterTag(id) {
      let string = state.characterArray.splice(id, 1);
      
      let charId = await getCharacterByName(string[0])

      state.issue.characterTags = state.issue.characterTags.replace(',' + charId + ',', ',');
    }

    function closeMe() {
      context.emit('close-issue', null)
    }

    function onFileChange(e) {
      var files = e.target.files || e.dataTransfer.files;
      if (!files.length)
        return;
      this.createImage(files[0]);
    }

    function createImage(file) {
      let image = new Image(180, 280);
      let reader = new FileReader();
      reader.onload = (e) => {
        image = e.target.result;
        state.issue.image = image;
      };
      reader.readAsDataURL(file);
    }

    function removeImage() {
      state.issue.image = '';
    }


    return {
      state,
      postIssue,
      files,
      validate,
      submit,
      selectionChanged,
      clearIssue,
      closeMe,
      edit,
      selectedCharacterChanged,
      remove,
      removeCharacterTag,
      onFileChange,
      createImage,
      removeImage
    }
  }
}
</script>

<style scoped>
  #close-btn {
    top: -50px;
    right: -55px;
    background-color: var(--red);
    color: white;
  }

  #browse-edit-btn {
    background-color: var(--green);
    color: white;
    border-radius: 8px;
    vertical-align: center;
  }

  #delete-btn {
    background-color: var(--red);
    color: white;
    border-radius: 8px;
    margin-left: 100px;
    vertical-align: center;
  }

  input {
    border: 2px solid var(--light-gray);
    padding-left: 10px;
    margin-top: 10px;
  }

  .drop-area {
    border: 1px solid var(--light-gray);
    border-radius: 8px;
    margin-top: 10px;
    margin-bottom: 10px;
    padding-top: 30px;
    padding-bottom: 30px;
  }

  .margin {
    margin-top: 20px;
  }

  .image {
    height: 400px; 
    max-width: 100%;
  }

  .image-preview {
    height: 100%;
    height: 400px;
  }

</style>