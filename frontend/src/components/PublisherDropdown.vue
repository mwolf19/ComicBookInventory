<template>
  <div>
    <select class="w3-select w3-round-large w3-card" id="dropdown" style="width: 215px;" v-model="state.selectedPublisherId" @change="selectionChanged()">
      <option value="" disabled selected><em>Please choose a publisher</em></option>

      <option v-for="(publisher, id) in state.publisherList" :key="id" :value="publisher.publisherId">{{ publisher.publisherName }}</option>
    </select>
  </div>
</template> 

<script>
  import { reactive, onMounted, watch } from 'vue';
  import { publisherRepository } from '@/store/repository/publisherRepository';
  
  export default {
    name: 'PublisherDropdown',
    
    emits: ['selected-publisher-changed'],

    props: ['selectedId'],

    setup(props, context) {
      onMounted(() => {
        loadPublishers();
      });

      const state = reactive({
        selectedPublisherId: 0,
        publisherList: []
      });

      watch(props, async function() {
        state.selectedPublisherId = props.selectedId
      });

      const {
        getActivePublishers
      } = publisherRepository();

      async function loadPublishers() {
        state.publisherList = await getActivePublishers();
      }

      function selectionChanged() {
        context.emit('selected-publisher-changed', state.selectedPublisherId);
      }

      return {
        state,
        loadPublishers,
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