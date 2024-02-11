<template>
    <v-card>
        <v-card-title>
            Создание карточки документа
        </v-card-title>
        <v-card-text>
            <v-text-field
                    label="Имя карточки"
                    v-model="card.title"
            />
            <date-picker
                :label="`Исполнить к:`"
                @get-date="setDate"/>
            <v-textarea
                    rows="2"
                    label="Комментарий"
                    v-model="card.comment"
                    prepend-icon="mdi-text-box-outline"
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
                    @click="$emit('cancel'); "
            >
                Отмена
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
import {mapMutations} from 'vuex'
import cardApi from "@/api/cardApi";
import DatePicker from "@/components/dialogs/DatePicker";
export default {
    name: "CreateCardDocument",

    data() {
        return {
            card: {
                id: null,
                userLogin: null,
                title: null,
                comment: null,
                executeTo: null,
            },
        }
    },
    components:{ DatePicker },
    methods: {
        ...mapMutations(['addCardToList',]),
        async save() {
            try {
                let response = await cardApi.saveCard(this.card)
                let data = response.data
                this.addCardToList(data)
            } catch (e) {
                console.log(e)
            } finally {
                this.$emit('cancel')
                this.card.id = null
                this.card.userLogin = null
                this.card.title = null
                this.card.comment = null
                this.card.executeTo = null
            }
        },
        setDate(date){
            this.card.executeTo = date
        }
    },
}
</script>

<style scoped>

</style>