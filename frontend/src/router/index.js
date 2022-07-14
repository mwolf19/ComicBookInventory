import { createRouter, createWebHistory } from 'vue-router';
import PublisherAddEdit from '@/views/publisher/PublisherAddEdit.vue';
import CharacterAddEdit from '@/views/character/CharacterAddEdit.vue';
import CharacterMaintenance from '@/views/character/CharacterMaintenance.vue';
import PublisherMaintenance from '@/views/publisher/PublisherMaintenance.vue';
import CreateAccount from '@/views/CreateAccount.vue';
import Login from '@/views/Login.vue';
import UserProfile from '@/views/profile/UserProfile.vue';
import HomeScreen from '@/views/HomeScreen.vue';

const routes = [
  {
    path: '/',
    name: 'Login',
    component: Login
  },
  {
    path: '/publisheraddedit',
    name: 'PublisherAddEdit',
    component: PublisherAddEdit
  },
  {
    path: '/characteraddedit',
    name: 'CharacterAddEdit',
    component: CharacterAddEdit
  },
  {
    path: '/character-maintenance',
    name: 'CharacterMaintenance', 
    component: CharacterMaintenance
  }, 
  {
    path: '/publisher-maintenance',
    name: 'PublisherMaintenance',
    component: PublisherMaintenance
  },
  {
    path: '/homescreen',
    name: 'HomeScreen',
    component: HomeScreen
  },
  {
    path:'/profile',
    name:'UserProfile',
    component: UserProfile
  },
  {
    path: '/create-account',
    name: 'CreateAccount',
    component: CreateAccount
  }, 
  {
    path: '/profile',
    name: 'UserProfile',
    component: UserProfile
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router