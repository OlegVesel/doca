<template>
    <div class="home">
        <img alt="Vue logo" src="../assets/logo.png">
        <div v-if="show">
            <div v-for="u in users" :key="u.id">
                {{ u.login }}
            </div>
        </div>
        <button @click="clearToken">exit</button>
        <button @click="getUsers">users</button>
    </div>
</template>

<script>
// @ is an alias to /src
import axios from "axios";
import {mapActions} from 'vuex'

export default {
    name: 'HomeView',
    data() {
        return {
            users: [],
            show : false
        }
    },
    components: {},
    methods: {
        ...mapActions(['logoutAction']),
        async getUsers() {
            let data = await axios.get("/api/user")
            this.users = data.data
            this.show = true
        },

        clearToken() {
            this.logoutAction()
        }
    },
}
</script>
