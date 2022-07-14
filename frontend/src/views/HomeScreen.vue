<template>
    <div>
        <!-- filtering -->
        <div class="w3-row">
            <Filtering @filtered-list="filterUpdated"/>
        </div>

        <!-- inventory display -->
        <div class="w3-row">
            <div class="w3-threequarter">
                <div class="w3-col" v-if="state.userComics.length !== 0">
                    <div class="w3-quarter" v-for="(comic, comicId) in state.userComics" :key="comicId" :value="comic.issueId">
                        <div class="w3-col comic w3-container"  style="padding: 50px; cursor:pointer;">

                            <a @click="openUpdateModal(comic.issueId)">
                                <div v-if="!comic.coverImage || comic.coverImage === ''">
                                    <img style="max-width: 100%" src="../assets/download.jpg">
                                </div>

                                <div v-else>
                                  <img style="max-width: 100%" id="image-file" :src="comic.coverImage" />
                                </div>
                            </a>

                            <p><em>{{ comic.issueName }}</em></p>

                            <p>Issue {{ comic.issueNumber }}</p>
                        </div>
                    </div>
                </div>

                <!-- The collection is blank -->
                <div class="w3-col" v-if="state.userComics.length === 0" id="blank-txt">
                    <h2 class="w3-padding">Your collection is blank!</h2>
                    <h4>Please click the add button at the<br> bottom left to add a comic.</h4>
                </div>
            </div>

            <!-- friends list -->
            <div class="w3-quarter">
                <div class="w3-container w3-margin">
                    <div class="w3-col">
                        <div class="w3-container w3-border w3-margin-bottom" id="friends-box">
                            <h2 style="color:var(--orange)"><strong>Friends Activity</strong></h2>

                            <div v-if="state.friendsActivity.length !== 0" class="friendsActivity w3-cell w3-rest w3-container w3-col w3-margin-bottom" style="overflow: scroll; overflow-x:hidden; height: 10%; display: block;">
                                <table class="w3-table w3-striped w3-bordered w3-centered w3-rest" style="position: sticky; display: table;">

                                    <tr class="tableHover" v-for="(activity, key) in state.friendsActivity" :key="key">
                                        <td @click="openActivityModal(activity.issueId)" style="cursor:pointer;">{{ activity.username }} {{ activity.activity }} <i>{{  activity.issueTitle }}</i> #{{ activity.issueNumber }}</td>
                                    </tr>

                                </table>
                            </div>

                            <div v-if="state.friendsActivity.length === 0" class=" w3-cell w3-rest w3-container w3-col w3-margin-bottom" style="overflow: scroll; overflow-x:hidden; height: 10%; display: block;">
                                <table class="w3-table w3-striped w3-bordered w3-centered w3-rest" style="position: sticky; display: table;">

                                    <tr>
                                        <td><h4>Please add friends to see their activity here.</h4></td>
                                    </tr>

                                </table>
                            </div>

                            <div class="w3-row">
                                <div id="search-box">
                                    <h5 style="color:var(--orange);"><strong>Add Friend</strong></h5>

                                    <input type="text" placeholder="Find users" id="friends-search" v-model="state.userSearch" @keyup="findUsers">

                                    <div class="results" style="text-align:left;" id="results">
                                        <div v-for="(user, key) in state.friendSearch" :key="key" style="list-style-type:none; margin-bottom:10px">
                                            {{ user.username }}
                                            <button class="w3-btn" style="background-color:var(--green); color:white; border-radius:4px; font-size:20px; float:right; height:25px; display:inline-flex; align-items:center;" 
                                                    @click="addFriend(user.userId)">+</button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
        
       <!-- Add a comic button -->
       <a @click="openAddModal" class="w3-btn w3-large w3-circle w3-ripple addButton w3-margin">+</a> 

       <Issue :showModal="state.showIssueModal" :issueId="state.selectedIssueId" @close-issue="closeIssue"/>
    </div>
</template>



<script>
    import { reactive, onMounted} from 'vue';
    import { useToast } from "primevue/usetoast";
    import { comicRepository } from "@/store/repository/comicRepository.js";
    import { friendActivityRepository } from "@/store/repository/friendActivityRepository.js";
    import { appGlobal } from "@/store/global/appGlobal.js";
    import { userRepository } from "@/store/repository/userRepository.js";
    import { friendRepository } from "@/store/repository/friendRepository.js";

    import Filtering from '@/components/Filtering.vue';
    import Issue from '@/views/Issue.vue'

    export default {
        name: 'HomeScreen',

        components: {
            Filtering,
            Issue
        },

        setup() {
            const state = reactive({
                userComics: [],
                showIssueModal: false,
                selectedIssueId: 0,
                friendsActivity: [],
                allUsers: [],
                friendSearch: [],
                userSearch:''
            });

            const toast = useToast();

            const {
                getFriends,
                getFriendActivityUnshown
            } = friendActivityRepository();

            const {
                getUsersForSearch
            } = userRepository();

            const {
                updateFriendsList
            } = friendRepository();

            const comicRepo = comicRepository();
    
            onMounted(async () => {
                await getCollection(appGlobal.user.userId);

                await getFriendsActivity();  
            });

            async function getCollection(ID) {
                state.userComics = await comicRepo.getAllUserComics(ID);
            }

            async function getFriendsActivity() {
                let friendsList = await getFriends(appGlobal.user.userId);

                appGlobal.user.friendsList = friendsList.friends;

                let friends = appGlobal.user.friendsList.split(',');

                let holder = [];

                state.friendsActivity = [];

                friends.forEach(async (friend) => {
                    holder = await getFriendActivityUnshown(friend);

                    holder.forEach(element => {
                        state.friendsActivity.push(element)
                    })
                });
            }

            function openUpdateModal(issueId) {
                state.selectedIssueId = issueId;
                state.showIssueModal = true;
            }

            //Opens the add modal to add a comic 
            function openAddModal() {
                state.selectedIssueId = 0;
                state.showIssueModal = true;
            }

            function openActivityModal(issueId) {
                state.selectedIssueId = issueId;
                state.showIssueModal = true;
            }

            async function closeIssue(success) {
                if (success) {
                    await getCollection(appGlobal.user.userId);
                }

                state.showIssueModal = false;
            }

            function filterUpdated(newList) {
                state.userComics = newList;
            }

            //Search users and show matching results         
            async function findUsers() {
                if (state.userSearch !== ''){
                    state.friendSearch = await getUsersForSearch(state.userSearch);
                } else {
                    state.friendSearch = [];
                }
            }
                
            async function addFriend(userId){
                
                let friendArray = appGlobal.user.friendsList.split(',');

                if ((friendArray.includes(userId.toString()) === false)) {
                    if (appGlobal.user.userId !== userId) {
                        appGlobal.user.friendsList += ',' + userId;

                        let success = await updateFriendsList(appGlobal.user.userId, appGlobal.user.friendsList);

                        if (success === 1) {
                            await getFriendActivityUnshown(appGlobal.user.userId);

                            state.userSearch = '';
                            state.friendSearch = [];

                            toast.add({severity:'success', summary: 'Success', detail:'Friend added', life: 3000});
                        }
                    } else {
                        toast.add({severity:'error', summary: 'Friend Error', detail:'You cannot add yourself', life: 3000});
                    }
                } else {
                    toast.add({severity:'error', summary: 'Friend Error', detail:'Friend already in list', life: 3000});
                }
            }

            return {
                state,
                openAddModal,
                openUpdateModal,
                openActivityModal,
                closeIssue,
                filterUpdated, 
                findUsers, 
                addFriend,
                getFriends,
                getFriendActivityUnshown
            }
        }
    }
</script>


<style scoped>
    .addButton {
        z-index: 100;
        float: left;
        position: fixed; 
        bottom: 20px;
        left: 30px;
        background-color: var(--purple);
        color: white;
    }

    #blank-txt {
        border: 1px solid var(--light-gray);
        border-radius: 8px;
        width: 50%;
        transform: translate(50%, 30%);
        background-color: var(--light-orange);
        padding: 10px;
        margin-bottom: 5em;
    }

    .comic:hover {
        background: var(--light-gray);
        color: black;
    }

    #friends-box {
        border-radius: 8px;
        line-height: 30px;
    }

    #friends-search {
        background-image: url('../assets/images/searchIcon.png');
        background-repeat: no-repeat;
        background-size: 8%;
        background-position: 5px;
        padding-left: 30px;
        border: 2px solid var(--light-gray);
        border-radius: 8px;
    }

    #search-box {
        margin-top: 25px;
        margin-bottom: 15px;
        border-top: 2px solid var(--gray);
    }
    
    #results {
    padding-top: 3px;
    }

    /* Scrollbar design */
    ::-webkit-scrollbar {
    width: 10px;
    }
    ::-webkit-scrollbar-track {
    background: #f1f1f1;
    }
    ::-webkit-scrollbar-thumb {
    background: var(--light-gray);
    }
    ::-webkit-scrollbar-thumb:hover {
    background: var(--gray);
    }

    .friendsActivity{
        max-height: 40rem;
        overflow:hidden;
        overflow-x: scroll;
    }

    .tableHover:hover {
        background: lightgrey;
        color: black;
    }
</style>