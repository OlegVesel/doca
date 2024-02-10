<template>
    <v-card>
        <v-row class="pa-0 ma-0">
            <v-col cols="2">
                <h3 v-if="card.title"> {{ card.title }} </h3>
                <h3 v-else>Без названия</h3>
            </v-col>
            <v-col cols="2">
                {{card.created}}
            </v-col>
            <v-col cols="6">
                <v-chip color="green lighten-1">
                    Файлов в карточке: {{card.documents.length}}
                </v-chip>
            </v-col>
            <v-col cols="2" align="end">
                <v-btn
                        color="primary"
                        icon
                        small
                        @click="setChangedCard(card)"
                >
                    <v-icon>mdi-pencil-outline</v-icon>
                </v-btn>
                <v-btn
                        color="error"
                        icon
                        small
                        @click="showDialogDelete = true"
                >
                    <v-icon>mdi-delete-outline</v-icon>
                </v-btn>
            </v-col>
        </v-row>
        <!--        диалог для редактирования карточки-->
        <v-dialog
                v-model="showCreateCard"
                max-width="800"
                persistent
        >
            <update-card-document
                    @cancel="showCreateCard = false"
                    :changeCard="changedCard"
            />
        </v-dialog>
        <!--        диалог для удаления карточки-->
        <v-dialog
            v-model="showDialogDelete"
            max-width="400"
        >
            <confirm-dialog
                :title-text="`Удаление карточки`"
                :dialog-text="`Вы точно хотите удалить карточку ${card.title}?`"
                :color="`#E57373`"
                @cancel="showDialogDelete = false"
                @confirm="deleteCard(card.id)"
            />
        </v-dialog>
    </v-card>
</template>

<script>
import UpdateCardDocument from "@/components/document/UpdateCardDocument";
import ConfirmDialog from "@/components/dialogs/ConfirmDialog";
import {mapActions} from "vuex";

export default {
    name: "DocumentRow",
    props: ['card'],
    data() {
        return {
            showCreateCard: false,
            showDialogDelete: false,
            changedCard : null,
        }
    },
    components: {
        ConfirmDialog, UpdateCardDocument
    },
    methods:{
        ...mapActions(['deleteCardFromServer']),
        deleteCard(id){
            this.deleteCardFromServer(id)
        },
        setChangedCard(card){
            this.changedCard = card
            this.showCreateCard = true
        }
    }
}
</script>

<style scoped>

</style>