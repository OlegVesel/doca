<template>
    <v-snackbar
            app
            right
            top
            :color="color"
            text
            elevation="16"
            :timeout=timeout
            :value="true"
    >
        <v-row align="center">
            <v-col cols="1">
                <v-icon
                        :color="color"
                >
                    {{ icon }}
                </v-icon>
            </v-col>
            <v-col>
                <span style="font-size: 16px">
                    {{ text }}
                </span>
            </v-col>
        </v-row>


        <template v-slot:action="{ attrs }">
            <v-btn
                    color="error"
                    icon
                    v-bind="attrs"
                    @click="$emit('hide', index)"
            >
                <v-icon>mdi-close</v-icon>
            </v-btn>
        </template>
    </v-snackbar>
</template>

<script>
export default {
    name: "NotificationSnackbar",
    props: ['text', 'color', 'icon', 'index', 'timeout'],
    data() {
        return {}
    }
}
</script>

<style scoped>

</style>