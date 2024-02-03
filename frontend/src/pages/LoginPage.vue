<template>
    <div>
        <label>LOGIN</label>
        <input v-model="account.login"/>
        <label>password</label>
        <input v-model="account.password"/>
        <button @click="authUser">click</button>
        {{account}}
    </div>
</template>

<script>
import {mapActions} from 'vuex'
export default {
    name: "LoginPage",
    data() {
        return {
            account:{
                login: null,
                password: null,

            }
        }
    },
    methods:{
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