import { reactive } from 'vue';

const appGlobal = reactive({
  apiBaseUrl: '',
  user: {
    userId: 0,
    username: '',
    password: '',
    friendsList: []
  },
  
  clearUser() {
    appGlobal.user.userId = 0;
    appGlobal.user.username = '';
    appGlobal.user.password = '';
  }
}); 

export { appGlobal };