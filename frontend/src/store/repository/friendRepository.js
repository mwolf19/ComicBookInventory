import { ref } from 'vue';

export function friendRepository(){
  const friendId = ref(0);
  const foundFriend = ref([]);

  async function saveFriend(friend) {
    const url = 'http://localhost:8080/friend/saveFriend';

    friendId.value = 0;
    
    const options = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(friend)
    };

    await fetch(url, options)
    .then(response => response.json())
    .then(function (data) {
      friendId.value = data;
    })

    return friendId.value;
  }

  async function findFriend(friend) {
    const url = 'http://localhost:8080/friend/findFriend/' + friend;

    foundFriend.value = [];

    await fetch(url) 
    .then(response => response.json())
    .then(function (data) {
      foundFriend.value = data;
    })

    return foundFriend;
  }

  async function updateFriendsList(userId, friends) {
    const url = 'http://localhost:8080/friend/updateFriends/' + userId + '/' + friends;
    
    friendId.value = 0;
    
    const options = {
      method: "PUT",
      headers: { "Content-Type": "application/json" }
    };

    await fetch(url, options)
    .then(response => response.json())
    .then(function (data) {

      friendId.value = data;
    })

    return friendId.value;
  }
  
  return {
    saveFriend, 
    findFriend,
    updateFriendsList
  }
}