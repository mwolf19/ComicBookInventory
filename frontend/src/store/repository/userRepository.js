import { ref } from 'vue';

export function userRepository(){
    const loginValue = ref(0);
    const userId = ref(0);
    const user = ref({});
    const users = ref([]);
    const updateCconfirmation = ref(0);

    //Login function
    async function getLogin(user) {
      const url = "http://localhost:8080/user/login/" + user.username + "/" + user.password;

      loginValue.value = 0;

      await fetch(url)
      .then(response => response.json())
      .then(function (data) {
        loginValue.value = data;
      })

      return loginValue.value;
    }
   
    async function updatePpassword(user, new_password) {
      const url = "http://localhost:8080/user/updatePassword/" + user.username + "/" + user.password + "/" + new_password;

      updateCconfirmation.value = 0;

        await fetch(url)
        .then(response => response.json())
        .then(function (data) {
          updateCconfirmation.value = data;
        })

        return updateCconfirmation.value;
    }

    async function postUser(user) {
      const url = 'http://localhost:8080/user/createUser';

      userId.value = 0;

      const options = {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(user)
      };
      await fetch(url, options)
      .then(response => response.json())
      .then(function (data) {
        userId.value = data;
      })

      return userId.value;
    }

    async function getUserById(id) {
      const url = 'http://localhost:8080/user/getUserById/' + id;

      user.value = {};

      await fetch(url)
      .then(response => response.json())
      .then(function (data) {
        user.value = data;
      })
      return user.value;
  }

  //Search for users function
  async function getUsersForSearch(serachName) {
    const url = 'http://localhost:8080/user/userSearch/' + serachName;

    users.value = [];

    await fetch(url)
    .then(response => response.json())
    .then(function (data) {
      users.value = data;
    })

    return users;
  }


  return {
    getLogin,
    postUser,
    getUserById, 
    getUsersForSearch,
    updatePpassword, 
  }
}