import { ref } from 'vue';

export function publisherRepository() {
  const publishers = ref([]);
  const publisher = ref({});
  const publisherId = ref(0);

  async function getActivePublishers() {
    const url = 'http://localhost:8080/publisher/listAllActive';

    publishers.value = [];

    await fetch(url)
    .then(response => response.json())
    .then(function (data) {
      publishers.value = data;
    })

    return publishers;
  }

  async function getPublisherById(id) {
    const url = 'http://localhost:8080/publisher/listById/' + id;

    publisher.value = {};

    await fetch(url)
    .then(response => response.json())
    .then(function (data) {

      publisher.value = data;
    })

    return publisher;
  }

  async function postPublisher(publisher) {
    const url = 'http://localhost:8080/publisher/savePublisher';

    publisherId.value = 0;

    const options = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(publisher)
    };

    await fetch(url, options)
    .then(response => response.json())
    .then(function (data) {
      publisherId.value = data;
    })

    return publisherId.value;
  }

  async function putPublisher(publisher) {
    const url = 'http://localhost:8080/publisher/updatePublisher';
    
    publisherId.value = 0;

    const options = {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(publisher)
    };

    await fetch(url, options)
    .then(response => response.json())
    .then(function (data) {
      publisherId.value = data;
    })

    return publisherId.value;
  }

  async function deletePublisher(id) {
    const url = 'http://localhost:8080/publisher/deletePublisher/' + id;

    publisherId.value = 0;

    const options = {
      method: "PUT"
    };

    await fetch(url, options)
    .then(response => response.json())
    .then(function (data) {
    
      publisherId.value = data;
    })

    return publisherId.value;
  }

  return {
    getActivePublishers,
    getPublisherById,
    postPublisher,
    putPublisher,
    deletePublisher
  };
}