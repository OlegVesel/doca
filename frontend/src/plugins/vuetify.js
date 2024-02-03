import Vue from 'vue';
import Vuetify from 'vuetify/lib/framework';

Vue.use(Vuetify);

export default new Vuetify({
    theme: {
        themes: {
            light: {
                error : '#EF5350'
            },
            dark:{
                error: '#9FA8DA'
            }
        },
    },

});
