<template>
    <v-container>
        <v-row>
            <h2>Управление пользователями</h2>
        </v-row>
        <v-row class="my-2">
            <v-btn
                    color="success"
                    @click="showUserCreateForm = true"
            >
                Создать
            </v-btn>
            <v-spacer/>
            <v-btn
                    icon
                    color="primary"
                    @click="updateListUsers"
            >
                <v-icon>mdi-autorenew</v-icon>
            </v-btn>
        </v-row>
        <v-dialog
                v-model="showUserCreateForm"
                max-width="600"
        >
            <user-create-form
                    @cancel="showUserCreateForm = false"
            />
        </v-dialog>
        <v-row v-for="user in getUsers" :key="user.id" class="my-2">
            <v-col class="px-0 py-2">
                <v-card>
                    <v-card-title>
                        {{user.fullName}} ({{ user.login }})
                    </v-card-title>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import UserCreateForm from "@/components/users/UserCreateForm";
import {mapActions, mapGetters} from "vuex";

export default {
    name: "UserCreate",
    data() {
        return {
            showUserCreateForm: false,
        }
    },
    components: {
        UserCreateForm
    },
    computed: {
        ...mapGetters(['getUsers'])
    },
    methods: {
        ...mapActions(['getUsersAction']),
        async updateListUsers() {
            await this.getUsersAction()
        }
    }
}
</script>

<style scoped>

</style>