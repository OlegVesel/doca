<template>
    <v-card>
        <v-card-title class="headline blue-grey">
            Создание пользователя
        </v-card-title>
        <v-card-text>
            <v-text-field
                    label="Логин пользователя"
                    v-model="user.login"
            />
            <v-text-field
                    label="Пароль пользователя"
                    v-model="user.password"
            />
            <v-text-field
                    label="Имя пользователя"
                    v-model="user.firstName"
            />
            <v-text-field
                    label="Фамилия пользователя"
                    v-model="user.lastName"
            />
            <v-text-field
                    label="Отчество пользователя"
                    v-model="user.patronymic"
            />

        </v-card-text>
        <v-card-actions>
            <v-btn
                    color="success"
                    @click="save"
            >
                Создать
            </v-btn>
            <v-btn
                    color="error"
                    @click="$emit('cancel')"
            >
                Отмена
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
import authApi from "@/api/authApi";
export default {
    name: "UserCreateForm",
    data(){
        return{
            user:{
                login: null,
                password: null,
                firstName: null,
                lastName: null,
                patronymic: null,
            }
        }
    },
    methods:{
        async save(){
            try{
                await authApi.registration(this.user)
            } catch (e){
                alert('Что-то пошло не так, возможно такой логин уже существует')
            }
            this.$emit('cancel')
        }
    }
}
</script>

<style scoped>

</style>