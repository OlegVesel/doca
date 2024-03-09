<template>
    <v-card>
        <v-text-field v-model="order.loginExecutor" label="login"></v-text-field>
        <v-btn @click="send">send</v-btn>
    </v-card>
</template>

<script>
import orderApi from "@/api/orderApi";

export default {
    name: "OrderForm",
    props: ['card'],
    data() {
        return {
            order: {
                id: null,
                loginExecutor: null,
                cardId: null,
                executeTo: '2024-03-09T23:35:00',
                executed: false
            }
        }
    },
    methods: {
        async send() {
            this.order.cardId = this.card.id
            let response = await orderApi.assignExecutor(this.order)
            console.log(response)
            this.$emit('cancel')
        },
    },
}
</script>

<style scoped>

</style>