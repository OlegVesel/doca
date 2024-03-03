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
                v-model="showNotification"
                multi-line
                app
                right
                top
                color="success"
                text
                elevation="24"
                :timeout=3000
        >
            {{currentSnackbar.text}}
        </v-snackbar>
    </v-app>
</template>

<script>
import ListNavigation from "@/components/navigation/ListNavigation";
import {addNotification, connect} from "@/api/wsApi";
export default {
    name: 'App',

    data() {
        return {
            showNotification:false,
            currentSnackbar:{
                text: '',
            }
        }
    },
    watch:{

    },
    methods:{
        handleNotification(notification){
            this.currentSnackbar.text = 'Вам назначен документ на исполнение к ' + notification.executeTo
            this.showNotification = true
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

