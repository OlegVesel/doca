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
            <file-input
                    @getFile="setFile"
                />
            <v-simple-table v-if="card.documents.length > 0">
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
                            :key="doc.title"
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
import {mapMutations} from "vuex";

export default {
    name: "UpdateCardDocument",
    props: ['changeCard'],
    data() {
        return {
            card: {
                id: null,
                userLogin: null,
                title: null,
                documents: []
            },
            tempDoc:[]
        }
    },
    components:{
        FileInput
    },
    methods: {
        ...mapMutations(['replaceCardInList']),
        async update() {
            try {
                this.card.documents = this.tempDoc
                let response = await cardApi.updateCard(this.card)
                let data = response.data
                this.replaceCardInList(data)
            } catch (e) {
                console.log(e)
            }
        },
        setCard(newVal) {
            console.log(newVal)
            this.card.id = newVal.id
            this.card.userLogin = newVal.user.login
            this.card.title = newVal.title
            this.card.documents = newVal.documents
            this.tempDoc = []
        },
        setFile(files){
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