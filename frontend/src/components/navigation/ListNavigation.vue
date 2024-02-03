<template>
    <v-list
            nav
    >
        <v-list-item @click="goTo('documentList')">
            <v-list-item-icon>
                <v-icon>mdi-folder-outline</v-icon>
            </v-list-item-icon>
            <v-list-item-title>Мои документы</v-list-item-title>
        </v-list-item>
        <v-list-item link>
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
            <v-list-item-title >Выйти</v-list-item-title>
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
        ...mapGetters(['isAdmin']),
        admin() {
            return this.isAdmin
        }
    },
    methods: {
        ...mapActions(['logoutAction']),
        goTo(name) {
            this.$router.push({name})
        },
        logout() {
            this.logoutAction()
        }

    }
}
</script>

<style scoped>

</style>