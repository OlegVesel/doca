<template>
    <v-app id="globalApp">
        <v-system-bar
                app
                color="blue-grey darken-1"
                height="65"
        >
            <v-toolbar-title>
                <router-link
                        to="/"
                        tag="span"
                        style="cursor: pointer; color : white"
                        class="pl-6"
                >
                    «ДОКА»
                </router-link>
            </v-toolbar-title>
            <v-spacer></v-spacer>

        </v-system-bar>
        <v-navigation-drawer
                app
                expand-on-hover
                absolute
                v-if="$route.fullPath !== '/login'"
        >
           <list-navigation/>
        </v-navigation-drawer>

        <v-main class="px-0">
            <v-container fluid>
                <router-view></router-view>
            </v-container>
        </v-main>
        <v-snackbar
                v-for="(notification, i) in notifications" :key="i"
                v-model="showNotification"
                multi-line
                app
                right
                top
                color="success"
                text
                elevation="24"
                :timeout=-1
                :style="{'margin-top':calcMargin(i)}"
        >
            {{notification.text}}
            <template v-slot:action="{ attrs }">
                <v-btn
                        color="error"
                        icon
                        v-bind="attrs"
                        @click="hide(i)"
                >
                    <v-icon>mdi-close</v-icon>
                </v-btn>
            </template>
        </v-snackbar>
    </v-app>
</template>

<script>
import ListNavigation from "@/components/navigation/ListNavigation";
import {addNotification, connect} from "@/api/wsApi";
import {mapMutations} from "vuex";
export default {
    name: 'App',

    data() {
        return {
            showNotification : true,
            currentSnackbar:{
                text: '',
            },
            notifications:[],
        }
    },
    watch:{

    },
    methods:{
        ...mapMutations(['addCardToList']),
        handleNotification(notification){
            let item = {
                text : `Вам назначен документ ${notification.cardExecutor.title} на исполнение к  ${notification.executeTo}`
            }
            // this.currentSnackbar.text = `Вам назначен документ ${notification.card.title} на исполнение к  ${notification.executeTo}`
            this.notifications.push(item)
            this.addCardToList(notification.cardExecutor)
            this.showNotification = true
        },
        calcMargin(i) {
            return (i*80) + 'px'
        },
        hide(i){
            this.notifications.splice(i,1)
        }
    },
    components:{
      ListNavigation
    },
    created() {
        connect()
        addNotification(data => {
            this.handleNotification(data)
        })
    }

};
</script>

