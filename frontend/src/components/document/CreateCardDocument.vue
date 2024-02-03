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
export default {
    name: "CreateCardDocument"
}
</script>

<style scoped>

</style>