<template>
    <v-menu
            ref="menu"
            v-model="menu"
            :close-on-content-click="false"
            transition="scale-transition"
            offset-y
            min-width="290px"
    >
        <template v-slot:activator="{ on, attrs }">
            <v-text-field
                    v-model="date"
                    class="pa-0 ma-0"
                    readonly
                    v-bind="attrs"
                    v-on="on"
                    :label="label"
                    prepend-icon="mdi-clock-alert-outline"
            >

            </v-text-field>
        </template>
        <v-date-picker
                v-model="date"
                no-title
                scrollable
                locale="ru"
                first-day-of-week="1"
        >
            <v-spacer></v-spacer>
            <v-btn
                    text
                    color="primary"
                    @click="menu = false"
            >
                Отмена
            </v-btn>
            <v-btn
                    text
                    color="primary"
                    @click="getDate"
            >
                OK
            </v-btn>
        </v-date-picker>
    </v-menu>
</template>

<script>
export default {
    props: [
        'currentDate', 'label'
    ],

    data() {
        return {
            date: '',
            menu: false,
        }
    },
    watch: {
        currentDate() {
            this.date = this.currentDate
        }
    },
    methods: {
        getDate() {
            this.$emit('get-date', this.date)
            this.menu = false
        }
    },
    beforeMount() {
        this.date = this.currentDate
    },
}
</script>
