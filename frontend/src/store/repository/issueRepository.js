import { ref } from 'vue';

export function issueRepository() {
  const issues = ref([]);
  const issue = ref({});
  const issueId = ref(0);

  async function getActiveIssues() {
    const url = 'http://localhost:8080/issue/listAllActive';

    issues.value = [];

    await fetch(url)
    .then(response => response.json())
    .then(function (data) {
      issues.value = data;
    })

    return issues;
  }

  async function getIssuesbyUserIdAndPublisherId(userId, publisherId) {
    const url = 'http://localhost:8080/issue/listByUserIdAndPublisherId/' + userId + '/' + publisherId;

    issues.value = [];

    await fetch(url)
    .then(response => response.json())
    .then(function (data) {
      issues.value = data;
    })

    return issues;
  }

  async function getIssuesbyUserIdAndIssueNameLike(userId, issueName) {
    const url = 'http://localhost:8080/issue/filterBySearchName/' + userId + '/' + issueName;

    issues.value = [];

    await fetch(url)
    .then(response => response.json())
    .then(function (data) {
      issues.value = data;
    })

    return issues;
  }

  async function getIssuesbyUserIdAndCharacterId(userId, characterId) {
    const url = 'http://localhost:8080/issue/listByUserIdAndCharacterId/' + userId + '/' + characterId;

    issues.value = [];

    await fetch(url)
    .then(response => response.json())
    .then(function (data) {
      issues.value = data;
    })

    return issues;
  }

  async function getIssueById(id) {
    const url = 'http://localhost:8080/issue/listByIssueId/' + id;

    issue.value = {};

    await fetch(url)
    .then(response => response.json())
    .then(function (data) {
      issue.value = data;
    })

    return issue;
  }

  async function postIssue(issue) {
    const url = 'http://localhost:8080/issue/saveIssue';

    issueId.value = 0;

    const options = {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(issue)
    };

    await fetch(url, options)
    .then(response => response.json())
    .then(function (data) {
      issueId.value = data;
    })

    return issueId.value;
  }

  async function getAllUserComics(userID) {
    const url = "http://localhost:8080/issue/getUserCollection/" + userID;
    issues.value = [];

    //GET call
    await fetch(url)
    .then(response => response.json())
    .then(function (data) {
      issues.value = data;
    });

   return issues.value;
  }

  async function removeIssue(issue) {
    const url = "http://localhost:8080/issue/removeIssue/";

    issueId.value = 0;

    const options = {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(issue)
    };

    await fetch(url, options)
    .then(response => response.json())
    .then(function (data) {

      issues.value = data;
    });

    return issues.value;
  }

  return {
    getActiveIssues,
    getIssueById,
    postIssue,
    getIssuesbyUserIdAndPublisherId,
    getIssuesbyUserIdAndIssueNameLike,
    getIssuesbyUserIdAndCharacterId,
    getAllUserComics,
    removeIssue
  };
}