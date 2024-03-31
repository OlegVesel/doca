<template>
    <v-card>
        <v-card-title class="text-h5 pt-2 pb-0" :style="{'background-color': color}">
            <p style="word-break: break-word;">
                {{ titleText }}
            </p>
        </v-card-title>
        <v-card-text class="text--black">
            <h3>
                {{ dialogText }}
            </h3>
            <template v-if="card.executorOrder.needReport">
                <p class="red--text" v-if="reports.length === 0">Для подтверждения выполнения необходимо прикрепить файл
                    отчета!</p>
                <file-input ref="fileInputForm"
                            @getFile="setFile"
                />
            </template>
        </v-card-text>
        <v-card-actions class="justify-center">
            <v-btn
                    color="success"
                    @click="executeCard"
                    :disabled="isDisabled"
            >
                Ок
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
import FileInput from "@/components/document/FileInput";
import cardApi from "@/api/cardApi";
import {mapMutations} from "vuex";
export default {
    name: "CardExecuteDialog",
    props: ['titleText', 'dialogText', 'color', 'card'],
    data() {
        return {
            reports: [],
            cardRequest:{
                id: null,
                multipartFiles: [],
            }
        }
    },
    components: {
        FileInput
    },
    computed:{
        isDisabled(){
            return this.card.executorOrder.needReport && this.reports.length === 0
        }
    },
    methods: {
        ...mapMutations(['deleteCardFromList']),
        setFile(files) {
            this.reports = files
        },
        async executeCard(){
            this.cardRequest.id = this.card.id
            this.cardRequest.multipartFiles = this.reports
            let response = await cardApi.executeCard(this.cardRequest)
            console.log(response)
            this.deleteCardFromList(response.data.id)
        }
    }
}
</script>

<style scoped>

</style>