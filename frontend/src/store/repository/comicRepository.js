import { ref } from 'vue';

export function comicRepository() {
    const currentUserID = ref(0);
    const comicList = ref([]);

    async function getAllUserComics(userID) {
        const url = "http://localhost:8080/issue/getUserCollection/" + userID;
        currentUserID.value = userID;

        //GET call
        await fetch(url)
        .then(response => response.json())
        .then(function (data) {
            comicList.value = data;
        });

       return comicList.value;
    }

    return {
        getAllUserComics
    }
}