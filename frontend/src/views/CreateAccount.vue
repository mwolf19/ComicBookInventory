<template>
  <div>
    <div class="w3-container">
      <div class="w3-center">
        <h1 class="w3-text" id="header"><strong>Comic Book Inventory</strong></h1>
      </div>
    </div>

    <div class="w3-container body">
      <div class="w3-container w3-center" id="create-bg">

        <img src='../assets/images/userIcon.png' id="user-icon">

        <h2>Create New Account</h2>

        <div class="w3-row w3-padding">
          <label class="w3-col w3-right-align" style="width: 40%">Username</label>
          <input type="text" class="w3-col w3-card" id="username" placeholder="New Username" v-model="state.user.username" @keyup.enter="submit" />
        </div>

        <div class="w3-row w3-padding align-center">
          <label class="w3-col w3-right-align" style="width: 40%">Password</label>
          <input type="password" class="w3-col w3-card" id="password" placeholder="Create Password" v-model="state.user.password" @keyup.enter="submit"/>
        </div>

        <div class="w3-row w3-padding align-center">
          <label class="w3-col w3-right-align" style="width: 40%">Confirm Password</label>
          <input type="password" class="w3-col w3-card" id="password" placeholder="Re-Enter Password" v-model="state.user.confirmPassword" @keyup.enter="submit"/>
        </div>

        <div class="w3-row w3-padding">
          <div class="w3-container" style="background-color:var(--red); color:white" v-show="state.errorMessage !== ''">
            {{ state.errorMessage }}
          </div>
        </div>

        <div class="w3-cell-row w3-padding">
          <button class="w3-btn margin-right-15" id="create-btn" @click="submit">Create</button>
          <button class="w3-btn margin-left-15" id="cancel-btn" @click="cancel">Cancel</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import { reactive } from "vue";
  import { useRouter } from 'vue-router';
  import { userRepository } from '@/store/repository/userRepository.js'
  import { appGlobal } from '@/store/global/appGlobal.js';

  export default {
    name: "CreateAccount",

    setup() {
      const state = reactive({
        user: {
          username: "",
          password: "",
          confirmPassword: "",
        },
        errorMessage: "",
      });

      const router = useRouter();

      const {
        postUser,
        getUserById
      } = userRepository();

      async function validate() {
        state.errorMessage = "";

        if (state.user.username.trim() === "" || state.user.password.trim() === "" || state.user.confirmPassword.trim() === "") {
          state.errorMessage = "Please enter all fields";
        }
        if (state.user.password !== state.user.confirmPassword) {
          state.errorMessage = "Passwords do not match";
        }
      }

      async function submit() {
        await validate();
        if (state.errorMessage === "") {
           let newId = await postUser(state.user);
           
          if (newId === 0) {
            state.errorMessage = 'This username already exists in the system';

          } else if (newId === -1) {
            state.errorMessage = 'An error occured trying to add the username to the system';

          } else {
            appGlobal.user = await getUserById(newId);

            router.push('/homescreen');
          }
        }
      }

      function cancel() {
        router.push('/');
      }

      return {
        appGlobal,
        state,
        postUser,
        validate,
        submit,
        cancel
      };
    },
  };
</script>

<style scoped>
  input {
    width: 15rem;
  }

  label {
    align-items: left;
    padding-top: 5px;
    margin-right: 20px;
  }

  #header {
    color: var(--blue);
  }

  #create-bg {
    width: 50%;
    transform: translate(50%, 10%);
    background-color: white;
    border: 2px solid var(--light-gray);
    padding: 15px;
    border-radius: 8px;
  }

  #user-icon {
    height: 20%;
    width: 20%;
    margin-bottom: 10px;
  }

  #username {
    border: 1px solid var(--light-gray);
    border-radius: 8px;
    width: 200px;
    height: 30px;
    padding-left: 10px;
  }

  #password {
    border: 1px solid var(--light-gray);
    border-radius: 8px;
    width: 200px;
    height: 30px;
    padding-left: 10px;
  }

  #create-btn {
    background-color: var(--green);
    border-radius: 8px;
    color: white;
  }

  #cancel-btn {
    background-color: var(--red);
    color: white; 
    border-radius: 8px;
  }
</style>