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
                    max-width="800"
                    persistent
            >
                <create-card-document
                        @cancel="showCreateCard = false"
                />
            </v-dialog>
            <v-spacer></v-spacer>

            <v-switch
                    append-icon="mdi-delete"
                    color="error"
                    v-model="isShowDeleted"
            >
            </v-switch>
        </v-row>
        <v-row  v-for="card in getCards"
               :key="card.id">
            <v-col class="px-0 py-1">
                <document-row
                        :card="card"
                />
            </v-col>
        </v-row>

    </v-container>
</template>

<script>
import CreateCardDocument from "@/components/document/CreateCardDocument";
import DocumentRow from "@/components/document/DocumentRow";
import {mapActions, mapGetters} from "vuex";

export default {
    name: "DocumentList",
    data() {
        return {
            showCreateCard: false,
            isShowDeleted: false,
        }
    },
    watch:{
        isShowDeleted(newVal){
            if (newVal)
                this.getDeletedCardsFromServer()
            else
                this.getCardsFromServer()
        }
    },
    components: {
        CreateCardDocument, DocumentRow
    },
    computed: {
        ...mapGetters(['getCards'])
    },
    methods: {
        ...mapActions(['getCardsFromServer', 'getDeletedCardsFromServer']),

    },
    beforeMount() {
        this.getCardsFromServer()
    }
}
</script>

<style scoped>

</style>