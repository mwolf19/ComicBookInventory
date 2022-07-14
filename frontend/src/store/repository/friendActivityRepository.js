import { ref } from 'vue';

export function friendActivityRepository(){
  const array = ref([]);
  const object = ref({});

  async function getFriends(userId) {
    const url = 'http://localhost:8080/friend/listByUserId/' + userId;

    object.value = {};

    await fetch(url)
    .then(response => response.json())
    .then(function (data) {
      object.value = data;
    })

    return object.value;
  }

  async function getFriendActivity(userId) {
    const url = 'http://localhost:8080/friendActivity/findByUserId/' + userId;

    array.value = [];

    await fetch(url)
    .then(response => response.json())
    .then(function (data) {
      array.value = data;
    })

    return array.value;
  }

  async function getFriendActivityUnshown(userId) {
    const url = 'http://localhost:8080/friendActivity/findByUserIdAndShown/' + userId;

    array.value = [];

    await fetch(url)
    .then(response => response.json())
    .then(function (data) {
      array.value = data;
    })

    return array.value;
  }
  
  return {
    getFriends,
    getFriendActivity,
    getFriendActivityUnshown
  }
}