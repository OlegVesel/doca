<template>
    <v-container class="pt-6">
        <v-row justify="space-around">
            <v-card min-width="400"  class="mt-16 pa-6" @keyup.enter="authUser">
                <v-card-title>
                    <v-row justify="center">
                        <h2>Авторизация</h2>
                    </v-row>
                </v-card-title>
                <v-card-text class="pt-16">
                        <v-text-field
                                dense
                                label="Логин"
                                outlined
                                v-model="account.login"
                                prepend-inner-icon="mdi-account-outline"
                        ></v-text-field>
                        <v-text-field
                                dense
                                label="Пароль"
                                outlined
                                v-model="account.password"
                                prepend-inner-icon="mdi-lock-outline"
                        ></v-text-field>
                </v-card-text>
                <v-card-actions>
                    <v-row justify="center">
                        <v-btn
                                @click="authUser"
                                color="success"
                        >
                            Войти
                        </v-btn>
                    </v-row>
                </v-card-actions>
            </v-card>
        </v-row>
    </v-container>
</template>

<script>
import {mapActions} from 'vuex'

export default {
    name: "LoginPage",
    data() {
        return {
            account: {
                login: null,
                password: null,

            }
        }
    },
    methods: {
        ...mapActions(['loginAction']),
        authUser() {
            this.loginAction(this.account)
            this.account.login = ''
            this.account.password = ''
        }
    }
}
</script>

<style scoped>

</style>