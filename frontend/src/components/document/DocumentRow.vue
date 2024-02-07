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
                        @click="showCreateCard = true"
                >
                    <v-icon>mdi-pencil-outline</v-icon>
                </v-btn>
                <v-btn
                        color="error"
                        icon
                        small
                >
                    <v-icon>mdi-delete-outline</v-icon>
                </v-btn>
            </v-col>
        </v-row>
        <v-dialog
                v-model="showCreateCard"
                max-width="800"
                persistent
        >
            <create-card-document
                    @cancel="showCreateCard = false"
                    :changeCard="card"
            />
        </v-dialog>
    </v-card>
</template>

<script>
import CreateCardDocument from "@/components/document/CreateCardDocument";

export default {
    name: "DocumentRow",
    props: ['card'],
    data() {
        return {
            showCreateCard: false,
        }
    },
    components: {
        CreateCardDocument,
    }
}
</script>

<style scoped>

</style>