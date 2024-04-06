<template>
    <v-container>
        <v-card
                class="mx-auto"
                max-width="600"
                shaped
        >
            <v-card-title>Профиль пользователя</v-card-title>
            <v-card-text>
                <v-row>
                    <v-col >
                        <v-text-field label="Фамилия" v-model="profile.lastName"></v-text-field>
                        <v-text-field label="Имя" v-model="profile.firstName"></v-text-field>
                        <v-text-field label="Отчество" v-model="profile.patronymic"></v-text-field>
                    </v-col>
                </v-row>
            </v-card-text>
            <v-card-actions>
                <v-btn
                    @click="update"
                    color="success"
                >
                    Изменить
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-container>
</template>

<script>
import {mapActions, mapGetters} from "vuex";

export default {

    name: "ProfilePage",
    data(){
        return{
            profile:{
                firstName: null,
                lastName : null,
                patronymic: null,
                password: null,
                roles: [],
            }
        }
    },
    computed:{
        ...mapGetters(['getUser']),
    },
    methods:{
        ...mapActions(['getUserByLogin', 'updateUser']),
        update(){
            this.updateUser(this.profile)
        }
    },
    beforeMount() {
        this.getUserByLogin(localStorage.getItem('login'))
        this.profile = this.getUser
    }
}
</script>

<style scoped>

</style>