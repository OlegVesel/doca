<template>
    <v-file-input
            v-model="tempDoc"
            label="Файлы в карточке"
            multiple
            prepend-icon="mdi-paperclip"
    >
        <template v-slot:selection="{ index, text }">
            <v-chip
                    v-if="index < 3"
                    color="blue-grey darken-1"
                    dark
                    label
                    small
            >
                {{ text }}
            </v-chip>

            <span
                    v-else-if="index === 3"
                    class="text-overline grey--text text--darken-3 mx-2"
            >
                        +{{ tempDoc.length - 3 }} Файла(ов)
                    </span>
        </template>
    </v-file-input>
</template>

<script>
export default {
    name: "FileInput",
    data(){
        return{
            tempDoc:[]
        }
    },
    watch:{
        tempDoc(newVal){
            this.$emit('getFile', newVal)
        }
    }

}
</script>

<style scoped>

</style>