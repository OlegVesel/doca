<template>
    <v-card>
        <v-card-title class="headline blue-grey">
            Назначение исполнителя
        </v-card-title>
        <v-card-text>
            <v-row class="mt-2">
                <v-autocomplete
                        :items="getUsers"
                        item-text="fullName"
                        item-value="login"
                        v-model="order.loginExecutor"
                        label="Выберите исполнителя"
                ></v-autocomplete>
            </v-row>
            <v-row class="align-end">
                <v-col class="pt-0">
                    <date-picker
                            label="Исполнить:"
                            :current-date="order.dateExecuteTo"
                            @get-date="setDate"/>
                </v-col>
                <v-col class="pt-0">
                    <v-text-field
                            label="к:"
                            type="time"
                            v-model="order.timeExecuteTo"
                    ></v-text-field>
                </v-col>
                <v-col>
                    <v-switch v-model="order.needReport" label="Предоставить отчет"></v-switch>
                </v-col>
            </v-row>
        </v-card-text>
        <v-card-actions>
            <v-btn
                    color="success"
                    :disabled="isDisabled"
                    @click="send">
                передать
            </v-btn>
            <v-btn
                    color="error"
                    @click="$emit('cancel')">
                Отмена
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
import orderApi from "@/api/orderApi";
import {mapActions, mapGetters} from "vuex";
import DatePicker from "@/components/dialogs/DatePicker";

export default {
    name: "OrderForm",
    props: ['card'],
    data() {
        return {
            order: {
                id: null,
                loginExecutor: null,
                cardId: null,
                executeTo: null,
                timeExecuteTo: '12:00',
                dateExecuteTo: null,
                executed: false,
                needReport : false,
            }
        }
    },
    components: {
        DatePicker
    },
    computed: {
        ...mapGetters(['getUsers']),
        isDisabled(){
            return this.order.loginExecutor == null
        }
    },
    methods: {
        ...mapActions(['getUsersAction', 'getCardById']),
        async send() {
            this.order.cardId = this.card.id
            if (this.order.executeTo != null)
                this.order.executeTo = `${this.order.dateExecuteTo}T${this.order.timeExecuteTo}`
            let response = await orderApi.assignExecutor(this.order)
            if (response.status === 200){
                await this.getCardById(this.card.id)
            }
            this.$emit('cancel')
        },
        setDate(date) {
            this.order.dateExecuteTo = date
        }
    },
    beforeMount() {
        if (!this.getUsers.length > 0)
            this.getUsersAction()
    }
}
</script>

<style scoped>

</style>