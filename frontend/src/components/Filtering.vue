<template>
  <div>
      <div class="margin-top-15">
        <div class="w3-row align-h-center align-v-center">
          <div class="w3-third w3-right-align font-size-large" ><strong>Search by: </strong></div>
  
          <div class="w3-rest margin-right-15 margin-left-15">
            <select class="w3-select w3-card" id="search-drop" style="width: 150px" v-model="state.selectedSearch">
              <option value="Title">Title</option>
              <option>Publisher</option>
              <option>Character</option>
            </select>
          </div>
  
          <div class="w3-quarter w3-left-align">
            <input type="text" class="w3-round-large w3-card" id="title-search" placeholder="Search for titles" style="width: 215px; height: 38px" v-show="state.selectedSearch === 'Title'" v-model="state.searchName" @keyup="titleSearchUpdate"> 
          
            <PublisherDropdown v-show="state.selectedSearch === 'Publisher'" :selectedId="state.selectedPublisher" @selected-publisher-changed="publisherChanged"/>
  
            <CharacterDropdown v-show="state.selectedSearch === 'Character'" :selectedId="state.selectedCharacter" @selected-character-changed="characterChanged"/>
          </div>
  
          <div class="w3-quarter w3-left-align">
            <button class="w3-btn" id="clear-btn" v-show="state.showClear === true" @click="clear">Clear</button>
          </div>
        </div>
      </div>
  </div>
</template> 

<script>
  import { reactive } from 'vue';
  import { issueRepository } from '@/store/repository/issueRepository.js';
  import { appGlobal } from '@/store/global/appGlobal.js'

  import PublisherDropdown from '@/components/PublisherDropdown.vue';
  import CharacterDropdown from '@/components/CharacterDropdown.vue';

  export default {
    name: 'Filtering',

    components: {
      CharacterDropdown,
      PublisherDropdown     
    },
    
    emits: ['filtered-list'],

    setup(props, context) {
      const state = reactive({
        selectedSearch: '',
        issuesList: [],
        searchName: '',
        selectedPublisher: '',
        selectedCharacter: '',
        showClear: false
      });

      const {
        getIssuesbyUserIdAndPublisherId,
        getIssuesbyUserIdAndIssueNameLike,
        getIssuesbyUserIdAndCharacterId,
        getAllUserComics
      } = issueRepository();

      async function titleSearchUpdate() {
        if (state.searchName !== '') {
          state.issuesList = await getIssuesbyUserIdAndIssueNameLike(appGlobal.user.userId, state.searchName);

          state.showClear = true;
        } else {
          state.issuesList = await getAllUserComics(appGlobal.user.userId);
        }

        context.emit('filtered-list', state.issuesList);
      }

      async function publisherChanged(publisherId) {
        state.selectedPublisher = publisherId;

        state.issuesList = await getIssuesbyUserIdAndPublisherId(appGlobal.user.userId, state.selectedPublisher);

        state.showClear = true;

        context.emit('filtered-list', state.issuesList);
      }

      async function characterChanged(characterId) {
        state.selectedCharacter = characterId;

        state.issuesList = await getIssuesbyUserIdAndCharacterId(appGlobal.user.userId, state.selectedCharacter);

        state.showClear = true;

        context.emit('filtered-list', state.issuesList);
      }

      async function clear() {
        state.selectedSearch = '';
        state.searchName = '';
        state.selectedPublisher = '';
        state.selectedCharacter = '';
        state.showClear = false;

        state.issuesList = await getAllUserComics(appGlobal.user.userId);

        context.emit('filtered-list', state.issuesList);
      }

      return {
        state,
        titleSearchUpdate,
        publisherChanged,
        characterChanged,
        clear
      }
    }
  }
</script>

<style scoped>
  #search-drop {
    border: 1px solid var(--gray);
    background-color: var(--light-orange);
    border-radius: 8px;
  }

  #title-search {
        background-image: url('../assets/images/searchIcon.png');
        background-repeat: no-repeat;
        background-size: 8%;
        background-position: 5px;
        padding-left: 30px;
        border: 1px solid var(--gray);
        border-radius: 8px;
    }

  #clear-btn {
    background-color: var(--gray);
    border-radius: 8px;
    color: white;
  }
</style>