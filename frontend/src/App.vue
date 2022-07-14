<template>
  <div>
    <Toast/>

    <div v-show="appGlobal.user.userId !== 0" class="align-h-center margin-bottom-15 margin-top-15">
      <button class="w3-btn" id="heading-btns" @click="doRoute">Home</button>
      <button class="w3-btn" id="heading-btns" @click="doRoute('publishermaintenance')">Publisher Maintenance</button>
      <button class="w3-btn" id="heading-btns" @click="doRoute('charactermaintenance')">Character Maintenance</button>
      <button class="w3-btn" id="heading-btns" @click="doRoute('profile')">Profile</button>
      <button class="w3-btn" id="logout-btn" @click="logout">Log Out</button>
    </div>
    
    <router-view />
  </div>
</template>

<script>
  import { useRouter } from 'vue-router';
  import { appGlobal } from '@/store/global/appGlobal';

  export default {
    name: 'App',

    setup() {
      const router = useRouter();

      function doRoute(whereTo) {
        switch (whereTo) {
          case 'productAddEdit':
            router.push('/publisheraddedit');
            break;

          case 'characterAddEdit':
            router.push('/characteraddedit');
            break;

          case 'publishermaintenance':
            router.push('/publisher-maintenance');
            break;

          case 'charactermaintenance':
            router.push('/character-maintenance');
            break;

          case 'profile':
            router.push('/profile');
            break;

          case 'logout':
            appGlobal.clearUser();
            router.push('/');
            break;

          default:
            router.push('/homescreen');
        }
      }

      function logout() {
        appGlobal.clearUser();
        router.push('/');
      }

      return {
        doRoute,
        appGlobal,
        logout
      }
    }
  }
</script>

<style>

#heading-btns {
  background-color: var(--blue);
  color: white;
  border-radius: 8px;
  margin-left: 15px;
}

#logout-btn {
  background-color: var(--red);
  color: white;
  border-radius: 8px;
  margin-left: 15px;
}

@import "./assets/styles/w3.css";

/* Colors to be used globally in css for consistency. 
      Call using the var() syntax (see below.) */
:root {
  --red: #ff3e3e;
  --light-red: #f79f9f;
  --orange: #fc880b;
  --light-orange: #fdbb74;
  --yellow: #ffd83c;
  --light-yellow: #f8dc6b;
  --green: #3abd3a;
  --light-green: #61eb61;
  --blue: #129de7;
  --light-blue: #c9ebf8;
  --purple: #a738a1;
  --light-purple: #ffadfb;
  --gray: #949393;
  --light-gray: #cfcece;
}

/* Stock (lame) */
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  /* margin-top: 60px; */
}

/* General */
th.cell-v-center,
td.cell-v-center {
  vertical-align: middle;
  display: table-cell;
}

td.cell-h-right {
  text-align: right;
  display: table-cell;
}

td.cell-h-left {
  text-align: left;
  display: table-cell;
}

td.cell-h-center {
  text-align: center;
  display: table-cell;
}

.align-h-center {
  display: flex;
  justify-content: center;
  flex-direction: row;
}

.align-v-center {
  align-items: center;
}

/* Color classes */
.color-red {
  background-color: var(--red);
}

.color-light-red {
  background-color: var(--light-red);
}

.color-orange {
  background-color: var(--orange);
}

.color-light-orange {
  background-color: var(--light-orange);
}

.color-yellow {
  background-color: var(--yellow);
}

.color-light-yellow {
  background-color: var(--light-yellow);
}

.color-green {
  background-color: var(--green);
}

.color-light-green {
  background-color: var(--light-green);
}

.color-blue {
  background-color: var(--blue);
}

.color-light-blue {
  background-color: var(--light-blue);
}

.color-purple {
  background-color: var(--purple);
}

.color-light-purple {
  background-color: var(--light-purple);
}

.color-gray {
  background-color: var(--gray);
}

.color-light-gray {
  background-color: var(--light-gray);
}

.font-color-red {
  color: var(--red);
}

/* Margin classes */
.margin-all-0 {
  margin: 0px;
}

.margin-all-5 {
  margin: 5px;
}

.margin-all-10 {
  margin: 10px;
}

.margin-all-15 {
  margin: 15px;
}

.margin-top-0 {
  margin-top: 0px;
}

.margin-top-5 {
  margin-top: 5px;
}

.margin-top-10 {
  margin-top: 10px;
}

.margin-top-15 {
  margin-top: 15px;
}

.margin-right-0 {
  margin-right: 0px;
}

.margin-right-5 {
  margin-right: 5px;
}

.margin-right-10 {
  margin-right: 10px;
}

.margin-right-15 {
  margin-right: 15px;
}

.margin-top-15 {
  margin-top: 15px;
}

.margin-bottom-0 {
  margin-bottom: 0px;
}

.margin-bottom-5 {
  margin-bottom: 5px;
}

.margin-bottom-10 {
  margin-bottom: 10px;
}

.margin-bottom-15 {
  margin-bottom: 15px;
}

.margin-left-0 {
  margin-left: 0px;
}

.margin-left-5 {
  margin-left: 5px;
}

.margin-left-10 {
  margin-left: 10px;
}

.margin-left-15 {
  margin-left: 15px;
}

/* Padding classes */

.padding-all-0 {
  padding: 0px;
}

.padding-all-5 {
  padding: 5px;
}

.padding-all-10 {
  padding: 10px;
}

.padding-all-15 {
  padding: 15px;
}

.padding-top-0 {
  padding-top: 0px;
}

.padding-top-5 {
  padding-top: 5px;
}

.padding-top-10 {
  padding-top: 10px;
}

.padding-top-15 {
  padding-top: 15px;
}

.padding-right-0 {
  padding-right: 0px;
}

.padding-right-5 {
  padding-right: 5px;
}

.padding-right-10 {
  padding-right: 10px;
}

.padding-right-15 {
  padding-right: 15px;
}

.padding-bottom-0 {
  padding-bottom: 0px;
}

.padding-bottom-5 {
  padding-bottom: 5px;
}

.padding-bottom-10 {
  padding-bottom: 10px;
}

.padding-bottom-15 {
  padding-bottom: 15px;
}

.padding-left-0 {
  padding-left: 0px;
}

.padding-left-5 {
  padding-left: 5px;
}

.padding-left-10 {
  padding-left: 10px;
}

.padding-left-15 {
  padding-left: 15px;
}

/* Font Sizes */
.font-size-tiny {
  font-size: 10px;
}

.font-size-small {
  font-size: 12px;
}

.font-size-medium {
  font-size: 14px;
}

.font-size-large {
  font-size: 16px;
}

.font-size-xlarge {
  font-size: 20px;
}

.body {
  background-image: linear-gradient(to bottom right, var(--purple), var(--blue), var(--light-blue));
  height: 500px;
}

input {
    border: 2px solid var(--light-gray);
    padding-left: 10px;
    border-radius: 8px;
  }
</style>