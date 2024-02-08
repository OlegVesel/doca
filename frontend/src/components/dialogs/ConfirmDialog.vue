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
        </v-card-text>
        <v-card-actions class="justify-center">
            <v-btn
                    color="success"
                    @click="$emit('confirm')"
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
export default {
    name: "ConfirmDialog",
    props: ['titleText', 'dialogText', 'color']
}
</script>

<style scoped>

</style>