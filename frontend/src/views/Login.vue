<template>
    <div>
        <div class="w3-container">
            <span class="w3-center">
                <h1 id="header"><strong>Comic Book Inventory</strong></h1>
            </span>
        </div>

        <div class="w3-container body">
            <div class="w3-container w3-center" id="login-bg">

                <img src='../assets/images/userIcon.png' id="user-icon">

                <h2 id="login">Log In</h2>

                <div class="w3-container">
                    <div class="w3-row align-center">
                        <label class="w3-right-align margin-right-10" for="username">Username </label>
                        <input class="w3-left-align w3-round-large w3-card" type="text" id="username" placeholder="Username" v-model="state.user.username" @keyup.enter="submit">
                    </div>
                
                    <div class="w3-row align-center">
                        <label class="w3-right-align margin-right-10" for="password">Password </label>
                        <input class="w3-left-align w3-round-large w3-card" type="password" id="password" placeholder="Password" v-model="state.user.password" @keyup.enter="submit">
                    </div>
                </div>
                
                <div>
                    <div class="w3-row align-center margin-top-10">
                        <div class="w3-container w3-red" v-show="state.errorMessage !== ''">
                            {{ state.errorMessage }}
                        </div>
                    </div>
                </div>

                <div class="w3-row align-h-center align-v-center margin-top-10">
                    <div>
                        <button class="w3-btn margin-right-15" id="login-btn" @click="submit">Login</button>
                    </div>

                    <div>
                        <button class="w3-btn margin-left-15" id="create-btn" @click="createAccount">New User</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import { reactive } from 'vue';
    import { useRouter } from 'vue-router';
    import { userRepository } from '@/store/repository/userRepository.js';
    import { appGlobal } from '@/store/global/appGlobal.js';

    export default {
        name: 'Login',
        setup() {

            const state = reactive({
                errorMessage: '',

                user: {
                    username: '',
                    password: ''
                }

            });

            const router = useRouter();

            const {
                getLogin,
                getUserById
            } = userRepository();

            async function validate() {
                //Checks for present state messages
                //Clears for another login attemp
                state.errorMessage = '';

                //Checks for empty input
                if (state.user.username === '' || state.user.password === '') {
                    state.errorMessage = 'Username and Password Are Required';
                    return;
                } 

                let loginStatus = await getLogin(state.user);

                return loginStatus;

            }

            async function submit() {
                let loginID = await validate();

                if (state.errorMessage === '') {

                    //Error Checking and Message Display
                    if (loginID === 0) { //User's details are wrong   

                        state.errorMessage = 'Login Attempt Was Unsuccessful';

                    } else if (loginID === -1) { //Internal Error

                        state.errorMessage = 'Internal error occured durring login, try again later';
                        
                    } else { //Correct Login   
                        appGlobal.user = await getUserById(loginID);

                        router.push('/homescreen');

                    }
                }
            }

            function createAccount() {
                router.push('/create-Account');
            }

            return {
                appGlobal,
                state,
                createAccount,
                submit,
                userRepository,
                validate
            }
        }
    }
</script>

<style scoped>
    #header {
        color: var(--blue);
    }

    #login-bg {
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
        margin-bottom: 15px;
        padding-left: 10px;
    }

    #password {
        border: 1px solid var(--light-gray);
        border-radius: 8px;
        width: 200px;
        height: 30px;
        margin-bottom: 10px;
        padding-left: 10px;
    }

    #login-btn {
        background-color: var(--green);
        border-radius: 8px;
        color: white;
    }

    #create-btn {
        background-color: var(--gray);
        color: white; 
        border-radius: 8px;
    }
</style>