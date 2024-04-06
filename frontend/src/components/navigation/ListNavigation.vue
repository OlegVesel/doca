<template>
    <v-list
            nav
    >
        <v-list-item link to="/documents" selectable color="primary">
            <v-list-item-icon>
                <v-icon>mdi-folder-outline</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Мои документы</v-list-item-title>
        </v-list-item>
        <v-list-item link to="/profile" selectable color="primary">
            <v-list-item-icon>
                <v-icon>mdi-account-outline</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Профиль</v-list-item-title>
        </v-list-item>
        <v-list-item link v-if="admin">
            <v-list-item-icon>
                <v-icon>mdi-cog-outline</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Настройки</v-list-item-title>
        </v-list-item>
        <v-divider></v-divider>
        <v-list-item @click="logout" class="red--text">
            <v-list-item-icon color="error">
                <v-icon class="red--text">mdi-account-arrow-right-outline</v-icon>
            </v-list-item-icon>
            <v-list-item-title>({{ account.login }}) Выйти</v-list-item-title>
        </v-list-item>
    </v-list>
</template>

<script>
import {mapActions, mapGetters} from 'vuex'

export default {
    name: "ListNavigation",
    data() {
        return {}
    },
    computed: {
        ...mapGetters(['isAdmin', 'getAccount']),
        admin() {
            return this.isAdmin
        },
        account() {
            return this.getAccount
        }
    },
    methods: {
        ...mapActions(['logoutAction']),
        logout() {
            this.logoutAction()
        }

    }
}
</script>

<style scoped>

</style>