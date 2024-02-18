<template>
    <v-card>
        <v-card-title>
            Редактирование карточки документа
        </v-card-title>
        <v-card-text>
            <v-text-field
                    label="Имя карточки"
                    v-model="card.title"
            />
            <date-picker
                    :label="`Исполнить к:`"
                    :current-date="card.executeTo"
                    @get-date="setDate"/>
            <v-textarea
                    rows="2"
                    label="Комментарий"
                    v-model="card.comment"
                    prepend-icon="mdi-text-box-outline"
            />
            <v-row>
                <v-col>
                    <file-input ref="fileInputForm"
                                @getFile="setFile"
                    />
                </v-col>
                <v-col>
                    <v-autocomplete
                            v-model="card.typeDocId"
                            :items="getTypeDocs"
                            item-value="id"
                            item-text="name"
                            label="Тип документа(ов)"
                    />
                </v-col>
            </v-row>
            <v-simple-table
                    v-if="card.documents.length > 0"
                    dense
            >
                <template v-slot:default>
                    <thead>
                    <tr>
                        <th class="text-left">
                            Заголовок
                        </th>
                        <th class="text-left">
                            Тип
                        </th>
                        <th class="text-left">
                            Действия
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr
                            v-for="doc in card.documents"
                            :key="doc.id"
                            :class="{ isDeleted : doc.isDeleted }"
                    >
                        <td>{{ doc.title }}</td>
                        <td>{{ doc.typeDoc?.name }}</td>
                        <td>
                            <v-btn v-if="!doc.isDeleted"
                                   icon
                                   x-small
                                   @click="prepareDocForDelete(doc)"
                            >
                                <v-icon color="error">mdi-delete-outline</v-icon>
                            </v-btn>
                            <v-btn v-else
                                   icon
                                   x-small
                                   @click="prepareDocForDelete(doc)"
                            >
                                <v-icon color="error">mdi-delete-outline</v-icon>
                            </v-btn>
                            <v-btn
                                    color="primary"
                                    icon
                                    x-small
                                    @click="getDocument(doc)"
                            >
                                <v-icon>mdi-arrow-down-bold-circle-outline</v-icon>
                            </v-btn>
                        </td>
                    </tr>
                    </tbody>
                </template>
            </v-simple-table>
            <v-dialog
                    v-model="showDialogDelete"
                    max-width="400">
                <confirm-dialog
                        :title-text="`Удаление документа`"
                        :dialog-text=textForDelete
                        :color="`#E57373`"
                        @cancel="showDialogDelete = false; idDocForDelete = null"
                        @confirm="deleteFile"
                />
            </v-dialog>
        </v-card-text>
        <v-card-actions>
            <v-btn
                    color="success"
                    @click="update"
            >
                Обновить
            </v-btn>
            <v-btn
                    color="error"
                    @click="tempDoc = []; $emit('cancel');"
            >
                Отмена
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
import cardApi from "@/api/cardApi";
import documentApi from "@/api/documentApi";
import FileInput from "@/components/document/FileInput";
import DatePicker from "@/components/dialogs/DatePicker";
import ConfirmDialog from "@/components/dialogs/ConfirmDialog";
import {mapActions, mapGetters, mapMutations} from "vuex";

export default {
    name: "UpdateCardDocument",
    props: ['changeCard'],
    data() {
        return {
            card: {
                id: null,
                userLogin: null,
                title: null,
                comment: null,
                executeTo: null,
                documents: [],
                typeDocId: null
            },
            docForDelete: {
                id : null,
                isDeleted : false,
            },
            textForDelete : '',
            showDialogDelete: false,
            tempDoc: []
        }
    },
    components: {
        FileInput, DatePicker, ConfirmDialog
    },
    computed: {
        ...mapGetters(['getCards', 'getTypeDocs']),
    },
    methods: {
        ...mapMutations(['replaceCardInList']),
        ...mapActions(['getTypeDocsFromServer', 'deleteFileFromCard', 'hardDeleteFileFromCard']),
        async update() {
            try {
                this.card['multipartFiles'] = this.tempDoc
                let response = await cardApi.updateCard(this.card)
                let data = response.data
                this.replaceCardInList(data)
                let newCard = this.getCards.find(card => card.id === data.id);
                this.setCard(newCard)
            } catch (e) {
                console.log(e)
            } finally {
                this.$refs.fileInputForm.clearTempDoc()
                this.tempDoc = []
            }
        },
        setCard(newVal) {
            this.card.id = newVal.id
            this.card.userLogin = newVal.userLogin
            this.card.title = newVal.title
            this.card.documents = newVal.documents
            this.card.comment = newVal.comment
            this.card.executeTo = newVal.executeTo

            this.tempDoc = []
        },
        setDate(date) {
            this.card.executeTo = date
        },
        setFile(files) {
            this.tempDoc = [...files]
        },
        prepareDocForDelete(doc){
            let {id ,isDeleted } = doc
            this.docForDelete.id = id
            this.docForDelete.isDeleted = isDeleted
            if (isDeleted)
                this.textForDelete = 'Документ будет полностью удален без возможности восстановления'
            else
                this.textForDelete = 'Вы точно хотите удалить документ? Его еще можно будет восстановить'
            this.showDialogDelete = true
        },
        deleteFile() {
            if (this.docForDelete.isDeleted){
                this.hardDeleteFileFromCard(this.docForDelete.id)
                let index = this.card.documents.findIndex(d => d.id === this.docForDelete.id);
                this.card.documents = [...this.card.documents.slice(0, index),
                    ...this.card.documents.slice(index + 1)]
            } else {
                this.deleteFileFromCard(this.docForDelete.id)
                let doc = this.card.documents.find(d => d.id === this.docForDelete.id);
                doc.isDeleted = true
            }
            this.showDialogDelete = false
        },
        async getDocument(doc){
            let { data } = await documentApi.print(doc.id)
            const href = URL.createObjectURL(data);
            const link = document.createElement('a');
            link.href = href;
            link.setAttribute('download', doc.title); //or any other extension
            document.body.appendChild(link);
            link.click();
            document.body.removeChild(link);
            URL.revokeObjectURL(href);
        }
    },
    beforeMount() {
        this.setCard(this.changeCard);
        if (!this.getTypeDocs.length > 0)
            this.getTypeDocsFromServer()
    }
}
</script>

<style scoped>
.isDeleted {
    background-color: #f8a1a1;
}
.isDeleted:hover {
    background-color: #f8a1a1 !important;
}
</style>