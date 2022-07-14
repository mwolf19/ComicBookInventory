<template>
    <div>
      <div class="w3-center">
        <h1><strong>Account Management</strong></h1>
      </div>

        <div class="body">
            <div class="w3-container" id="profile-content">
                <div class="w3-container margin-bottom-15">

                    <div class="w3-container margin-top-10">
                        <img src='@/assets/images/userIcon.png' id="user-icon">
                    </div>

                    <div class="w3-row align-h-center align-v-center field margin-top-15">
                        <label class="w3-third w3-right-align bold">Username:</label>
                        <label class="w3-third w3-left-align margin-left-10">{{appGlobal.user.username}}</label>
                    </div>

                    <div class="w3-row align-h-center align-v-center field">
                        <label class="w3-third w3-right-align bold">Password:</label>
                        <input type="password" class="w3-third w3-left-align margin-left-10" id="password"
                                v-model="appGlobal.user.password" disabled/>
                    </div>
                </div>

                <div class="w3-container w3-section">
                    <button class="w3-btn margin-right-10" id="misc-btns" @click="showModal">Update</button>
                    <button class="w3-btn margin-right-10" id="misc-btns" @click="togglePassword">Show / Hide Password</button>
                    <!-- <button class="w3-button w3-blue" @click="doRoute('')">Close</button>   -->
                </div>
            </div>

            <div v-show="state.modalShow == true" class="w3-modal" style="display: block;">
                <div class="w3-modal-content" id="modal-content">
                    <div class="w3-center padding-bottom-5">
                        <h2>Change Password</h2>
                    </div>

                    <button @click="showModal" style="position: fixed" class="w3-btn w3-circle w3-display-topright w3-card" id="modal-close">X</button>

                    <div style="min-width: 250px; min-height: 220px;">
                        <div class="w3-row align-h-center align-v-center w3-containter w3-section">
                            <label class="w3-cell w3-quarter bold">Current Password</label>

                            <input type="password" class="w3-cell w3-third w3-left-align w3-round-large w3-card padding-left-10" placeholder="Current Password" v-model="state.ChangePassword.current">
                        </div>

                        <div class="w3-row align-h-center align-v-center w3-containter w3-section">
                            <label class="w3-cell w3-quarter bold">New Password</label>
    
                            <input type="password" class="w3-cell w3-third w3-left-align w3-round-large w3-card padding-left-10" placeholder="New Password" v-model="state.ChangePassword.new">
                        </div>

                        <div class="w3-row align-h-center align-v-center w3-containter w3-section padding-bottom-10">
                            <label class="w3-cell w3-quarter bold">Confirm Password</label>

                            <input type="password" class="w3-cell w3-third w3-left-align w3-round-large w3-card padding-left-10" placeholder="Re-Enter New Password" v-model="state.ChangePassword.confirm">
                        </div>

                        <div class="w3-container w3-section w3-red">
                            <label class="align-h-center bold">{{state.errorMsg}}</label>
                        </div>

                        <div class="w3-container w3-section">
                            <button class="w3-btn margin-right-15" id="submit-btn" @click="submit">Submit</button>

                            <button class="w3-btn" id="cancel-btn" @click="showModal">Cancel</button>  
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
  import { reactive, ref } from 'vue';
  import { useRouter } from 'vue-router';
  import { useToast } from "primevue/usetoast";
  import {appGlobal} from '@/store/global/appGlobal.js';
  import { userRepository } from '@/store/repository/userRepository.js';
  export default {
      name: 'UserProfile',

      setup() {
        const state = reactive({
            show: false,
            modalShow: false,
            ChangePassword: {
                current: '',
                new: '',
                confirm: ''
            },
            errorShow: false,
            errorMsg: ''
        });

        const toast = useToast();

         const {
            updatePpassword,
            getUserById
        } = userRepository();

        function show() {
            state.show = !state.show;
        }

        const router = useRouter();

        function doRoute(whereTo) {
            router.push('/' + whereTo);
        }

        function clearChangePassword() {
            state.ChangePassword.current = '';
            state.ChangePassword.new = '';
            state.ChangePassword.confirm = '';
        }

        async function validate() {
            if (state.ChangePassword.current.trim() === '') {
                state.errorMsg = 'Please enter your current password';

            } else if(state.ChangePassword.new.trim() === '') {
                state.errorMsg = 'Please enter your new password';

            } else if(state.ChangePassword.confirm.trim() === '') {
                state.errorMsg = 'Please confirm your new password';

            } else if(state.ChangePassword.current !== appGlobal.user.password) {
                state.errorMsg = 'Incorrect Password';

            } else if (state.ChangePassword.new !== state.ChangePassword.confirm) {
                state.errorMsg = 'Confirm Password does not match New Password';

            } else if (state.ChangePassword.new.trim() === '' || state.ChangePassword.confirm.trim() === '') {
                state.errorMsg = 'New and Confirm Password cannot be blank';

            } else {
                state.errorMsg ='';
            }
        }

        async function submit() {
            const confVal = ref(0);

            state.errorMsg = '';

            await validate();

            if( state.errorMsg === '') {
                confVal.value = await updatePpassword(appGlobal.user, state.ChangePassword.new);


                if( confVal.value < 1) {
                    state.errorMsg = 'Error Changing password, could not find or authenticate user';
                } else {
                    appGlobal.user = await getUserById(appGlobal.user.userId);

                    toast.add({severity:'success', summary: 'Successful Update', detail:'Password updated', life: 4000});

                    showModal();
                }
            }
        }

        function showModal() {
            state.modalShow = !state.modalShow;
            state.errorMsg = '';
            clearChangePassword();
        }

        function togglePassword() {
          let input = document.getElementById('password');

          if (input.type === 'password') {
            input.type = 'text';

          } else {
            input.type = 'password';
          }
        }
        return {
            state,
            appGlobal,
            show,
            doRoute,
            showModal,
            submit,
            togglePassword,
            clearChangePassword
        }
      }
  }
</script>

<style scoped>

    input {
        border: 2px solid var(--light-gray);
    }

    #password {
        border-width: 0;
        background-color: transparent;
    }

    #profile-content {
        border: 2px solid var(--gray);
        border-radius: 8px;
        width: 50%;
        transform: translate(50%, 10%);
        padding-bottom: 15px;
        padding-top: 15px;
        background-color: white;
    }

    #user-icon {
        height: 15%;
        width: 15%;
    }

    #misc-btns {
        background-color: var(--gray);
        color: white;
        border-radius: 8px;
    }

    #close-btn {
        background-color: var(--red);
        color: white;
        border-radius: 8px;
    }

    .bold {
        font-weight: bold;
        text-align: right;
        padding-right: 15px;
    }

    .spacing {
        margin-top:25px;
    }

    #modal-content {
        border-radius: 8px;
        padding-top: 10px;
    }

    #modal-close {
        top: 20px;
        right: 30px;
    }

    #submit-btn {
        background-color: var(--green);
        color: white;
        border-radius: 8px;
        margin-top: 15px;
    }

    #cancel-btn {
        background-color: var(--red);
        color: white;
        border-radius: 8px;
        margin-top: 15px;
    }

</style>