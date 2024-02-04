<template>
    <v-container>
        <v-row>
            <h2>Мои документы</h2>
        </v-row>
        <v-row>
            <!--            кнопка для создания карточки-->
            <v-btn
                    color="success"
                    @click="showCreateCard = true"
            >
                Создать карточку документа
            </v-btn>
            <!--            диалог для создания карточки-->
            <v-dialog
                    v-model="showCreateCard"
                    max-width="400"
                    persistent
            >
                <create-card-document
                    @cancel="showCreateCard = false"
                />
            </v-dialog>
        </v-row>
        <v-row v-for="card in getCards" :key="card.id">
            {{card.id}}
        </v-row>
    </v-container>
</template>

<script>
import CreateCardDocument from "@/components/document/CreateCardDocument";
import {mapActions, mapGetters} from "vuex";

export default {
    name: "DocumentList",
    data() {
        return {
            showCreateCard: false,
        }
    },
    components: {
        CreateCardDocument
    },
    computed:{
        ...mapGetters(['getCards'])
    },
    methods:{
        ...mapActions(['getCardsFromServer'])
    },
    beforeMount() {
        this.getCardsFromServer()
    }
}
</script>

<style scoped>

</style>