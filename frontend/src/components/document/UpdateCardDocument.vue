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
                            :items="types"
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
                    </tr>
                    </thead>
                    <tbody>
                    <tr
                            v-for="doc in card.documents"
                            :key="doc.id"
                    >
                        <td>{{ doc.title }}</td>
                        <td>{{ doc.typeDoc?.name }}</td>
                    </tr>
                    </tbody>
                </template>
            </v-simple-table>
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
import FileInput from "@/components/document/FileInput";
import {mapGetters, mapMutations} from "vuex";
import DatePicker from "@/components/dialogs/DatePicker";

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
                typeDocId : null
            },
            type: {
                id: null,
                name: null
            },
            types: [
                {
                    id: 'bd767b6d-46eb-49fa-bfb3-8d210a5bfcaa',
                    name: 'Исходящий',
                },
                {
                    id: 'f8b55efa-1246-42be-bac4-30f81b0f66d6',
                    name: 'Входящий',
                },
                {
                    id: 'bde4700c-e7f3-4844-a3c5-654802233c80',
                    name: 'Не определен',
                }
            ],
            tempDoc: []
        }
    },
    components: {
        FileInput, DatePicker
    },
    computed: {
        ...mapGetters(['getCards']),
    },
    methods: {
        ...mapMutations(['replaceCardInList']),
        async update() {
            try {
                this.card.documents = this.tempDoc
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
            this.card.userLogin = newVal.user.login
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
        }
    },
    beforeMount() {
        this.setCard(this.changeCard)
    }
}
</script>

<style scoped>

</style>