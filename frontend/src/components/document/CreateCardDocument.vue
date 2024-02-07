<template>
    <v-card>
        <v-card-title>
            <template v-if="editMode">
                Редактирование карточки документа
            </template>
            <template v-else>
                Создание карточки документа
            </template>
        </v-card-title>
        <v-card-text>
            <v-text-field
                label="Имя карточки"
                v-model="card.title"
            />
            <v-simple-table v-if="card.documents.length > 0">
                <template v-slot:default>
                    <thead>
                    <tr>
                        <th class="text-left">
                            Заголовок
                        </th>
                        <th class="text-left">
                            Путь к файлу
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
                        <td class="text-truncate">{{ doc.pathToDoc }}</td>
                        <td class="text-truncate">{{ doc.typeDoc.name }}</td>
                    </tr>
                    </tbody>
                </template>
            </v-simple-table>
        </v-card-text>
        <v-card-actions>
            <v-btn v-if="!editMode"
                    color="success"
                    @click="save"
            >
                Создать
            </v-btn>
            <v-btn v-else
                    color="success"
                    @click="update"
            >
                Обновить
            </v-btn>
            <v-btn
                    color="error"
                    @click="$emit('cancel'); editMode = false"
            >
                Отмена
            </v-btn>
        </v-card-actions>
    </v-card>
</template>

<script>
import { mapMutations} from 'vuex'
import cardApi from "@/api/cardApi";

export default {
    name: "CreateCardDocument",
    props: ['changeCard'],
    data() {
        return {
            card: {
                id: null,
                userId: null,
                title: null,
                documents : []
            },
            editMode: false,
        }
    },
    watch:{
        changeCard(newVal){
            if (newVal !== null){
                this.editCard(newVal)
            }
        }
    },
    methods: {
        ...mapMutations(['addCardToList', 'replaceCardInList']),
        async save() {
            try {
                let response = await cardApi.saveCard(this.card)
                let data = response.data
                this.addCardToList(data)
                this.card.id = data.id
                this.editMode = true
            } catch (e) {
                console.log(e)
            }
        },
        async update(){
            try {
                let response = await cardApi.updateCard(this.card)
                let data = response.data
                this.replaceCardInList(data)
                this.editMode = true
            } catch (e) {
                console.log(e)
            }
        },
        editCard(newValue){
            this.card.id = newValue.id
            this.card.title = newValue.title
            this.card.documents = newValue.documents
            this.editMode = true
        }
    },
    beforeMount() {
        if (this.changeCard != null)
            this.editCard(this.changeCard)
    }
}
</script>

<style scoped>
.text-truncate {
    overflow: hidden;
    -webkit-line-clamp: 3;
    -webkit-box-orient: vertical;
    line-height: 1.3em;
}
</style>