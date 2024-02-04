<template>
    <v-card>
        <v-card-title>
            Создание карточки документа
        </v-card-title>
        <v-card-text>

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
import {mapActions} from 'vuex'
export default {
    name: "CreateCardDocument",
    data(){
        return{
            card:{
                id : null,
                userId : null,
            }
        }
    },
    methods:{
        ...mapActions(['saveCard']),
        async save(){
            await this.saveCard(this.card)
        }
    }
}
</script>

<style scoped>

</style>