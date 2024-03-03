<template>
    <v-card>
        <v-text-field v-model="order.loginExecutor" label="login"></v-text-field>
        <v-btn @click="send">send</v-btn>
    </v-card>
</template>

<script>
import { send} from '@/api/wsApi'

export default {
    name: "OrderForm",
    data() {
        return {
            order: {
                id: null,
                loginExecutor: null,
                cardId: null,
                executeTo: '2024-03-03T19:54:58',
                executed: false
            }
        }
    },
    methods: {
        send() {
            send(this.order)
            this.$emit('cancel')
        },
    },
}
</script>

<style scoped>

</style>