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

        <template v-if="showNotification">
            <notification-snackbar
                    v-for="(notification, i) in notifications" :key="i"
                    :color=notification.color
                    :text=notification.text
                    :icon=notification.icon
                    :timeout=notification.timeout
                    :index="i"
                    :style="{'margin-top':calcMargin(i)}"
                    @hide="hide"
            >
            </notification-snackbar>
        </template>
    </v-app>

</template>

<script>
import ListNavigation from "@/components/navigation/ListNavigation";
import NotificationSnackbar from "@/features/NotificationSnackbar";
import {addNotification} from "@/api/wsApi";
import {mapActions, mapMutations} from "vuex";

export default {
    name: 'App',

    data() {
        return {
            showNotification: true,
            currentSnackbar: {
                text: '',
            },
            notifications: [],
        }
    },
    watch: {},
    methods: {
        ...mapMutations(['addCardToList']),
        ...mapActions(['getCardById']),
        handleNotification(notification) {
            let item = {}
            switch (notification.type) {
                case 'ORDER' : {
                    item = {
                        text: `Вам передан документ ${notification.body.cardExecutor.title} ${notification.body.executeTo !== null ? ' на исполнение к ' + notification.body.executeTo : ''}`,
                        color: 'primary',
                        icon: 'mdi-information-outline',
                        timeout: -1
                    }
                    this.addCardToList(notification.body.cardExecutor)
                    break
                }
                case 'EXECUTE' : {
                    item = {
                        text: `${notification.body.userLogin} выполнил карточку ${notification.body.title}. ${notification.body.executorOrder.needReport !== null
                                ? ` Файл отчета находится в документах карточки` : ''}`,
                        color: 'success',
                        icon: 'mdi-check-bold',
                        timeout: -1
                    }
                    this.getCardById(notification.body.executorOrder?.cardCustomerId)
                    break
                }
                case 'CHANGE_USER' : {
                    item = {
                        text: notification.body,
                        color: 'success',
                        icon: 'mdi-check-bold',
                        timeout: -1
                    }
                    break
                }
            }
            this.notifications.push(item)
            this.showNotification = true
        },
        calcMargin(i) {
            return (i * 80) + 'px'
        },
        hide(i) {
            this.notifications.splice(i, 1)
        }
    },
    components: {
        ListNavigation, NotificationSnackbar
    },
    created() {
        addNotification(data => {
            this.handleNotification(data)
        })
    }

};
</script>

<style>
#globalApp{
    background: url("./assets/background.jpg") center no-repeat fixed;
}
</style>

