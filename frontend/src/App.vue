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
    </v-app>
</template>

<script>
import ListNavigation from "@/components/navigation/ListNavigation";
export default {
    name: 'App',

    data() {
        return {}
    },
    components:{
      ListNavigation
    },

};
</script>

