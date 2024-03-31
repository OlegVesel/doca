<template>
    <v-card>
        <v-row class="pa-0 ma-0">
            <v-col cols="2">
                <h3 v-if="card.title"> {{ card.title }} </h3>
                <h3 v-else>Без названия</h3>
            </v-col>
            <v-col cols="2">
                {{ card.created }}
            </v-col>
            <v-col cols="4">
                <v-chip color="green lighten-1">
                    Файлов в карточке: {{ card.documents?.length }}
                </v-chip>
            </v-col>
            <v-col cols="2">
                <v-tooltip
                        v-if="card.executorOrder !== null && card.executorOrder.executeTo !== null"
                        top
                >
                    <template v-slot:activator="{ on, attrs }">
                        <v-icon
                                color="warning"
                                v-bind="attrs"
                                v-on="on"
                        >
                            mdi-alert-outline
                        </v-icon>
                    </template>
                    <p class="tooltip__text">Карточка назначена {{ card.executorOrder.loginCustomer }} к {{ card.executorOrder.executeTo.split('T')[1] }}
                        {{ card.executorOrder.executeTo.split('T')[0] }}</p>
                </v-tooltip>
                <v-tooltip
                        v-if="card.customerOrder !== null && card.customerOrder.executeTo !== null"
                        top
                >
                    <template v-slot:activator="{ on, attrs }">
                        <v-icon
                                color="primary"
                                v-bind="attrs"
                                v-on="on"
                        >
                            mdi-information-outline
                        </v-icon>
                    </template>
                    <p class="tooltip__text">Над карточкой работают: {{ card.customerOrder.loginExecutors.length }}
                        [{{ card.customerOrder.loginExecutors.join(', ') }}]. </p>
                    <p class="tooltip__text">Исполнить к {{ card.customerOrder.executeTo.split('T')[1] }}
                        {{ card.customerOrder.executeTo.split('T')[0] }} </p>
                    <p class="tooltip__text">Осталось: {{ getTimeToExecute }}</p>
                </v-tooltip>
            </v-col>
            <v-col cols="2" align="end">
                <v-btn
                        v-if="card.executorOrder"
                        color="success"
                        icon
                        small
                        @click.stop="showExecuteCard = true"
                >
                    <v-icon>mdi-check-bold</v-icon>
                </v-btn>
                <v-btn
                        color="primary"
                        icon
                        small
                        @click="setChangedCard(card)"
                >
                    <v-icon>mdi-pencil-outline</v-icon>
                </v-btn>
                <v-btn
                        color="error"
                        icon
                        small
                        :disabled="isNoDelete"
                        @click="showDialogDelete = true"
                >
                    <v-icon>mdi-delete-outline</v-icon>
                </v-btn>
                <v-btn
                        color="indigo"
                        icon
                        small
                        @click="showOrderForm = true"
                >
                    <v-icon>mdi-send</v-icon>
                </v-btn>
            </v-col>
        </v-row>
        <!--        диалог для редактирования карточки-->
        <v-dialog
                v-model="showCreateCard"
                max-width="800"
                persistent
        >
            <update-card-document
                    @cancel="showCreateCard = false"
                    :changeCard="changedCard"
            />
        </v-dialog>
        <!--        диалог для удаления карточки-->
        <v-dialog
                v-model="showDialogDelete"
                max-width="400"
        >
            <confirm-dialog
                    :title-text="`Удаление карточки`"
                    :dialog-text="`Вы точно хотите удалить карточку ${card.title}? Все назначения связанные с этой карточкой также будут удалены` "
                    :color="`#E57373`"
                    @cancel="showDialogDelete = false"
                    @confirm="deleteCard(card.id)"
            />
        </v-dialog>
        <!--        диалог для назначения исполнителя-->
        <v-dialog
                v-model="showOrderForm"
                max-width="800"
        >
            <order-form
                    :card="card"
                    @cancel="showOrderForm = false"
            />
        </v-dialog>
        <!--        диалог для подтверждения выполнения карточки -->
        <v-dialog
                v-model="showExecuteCard"
                max-width="600"
        >
            <card-execute-dialog
                    :title-text="`Выполнение карточки`"
                    :dialog-text="`Карточка ${card.title} выполнена?`"
                    :color="`#43A047`"
                    :card="card"
                    @cancel="showExecuteCard = false"
            />
        </v-dialog>
    </v-card>
</template>

<script>
import UpdateCardDocument from "@/components/document/UpdateCardDocument";
import ConfirmDialog from "@/components/dialogs/ConfirmDialog";
import CardExecuteDialog from "@/components/dialogs/CardExecuteDialog";
import OrderForm from "@/components/document/OrderForm";
import {mapActions} from "vuex";

export default {
    name: "DocumentRow",
    props: ['card'],
    data() {
        return {
            showCreateCard: false,
            showDialogDelete: false,
            showOrderForm: false,
            showExecuteCard: false,
            changedCard: null,
        }
    },

    components: {
        ConfirmDialog, UpdateCardDocument, OrderForm, CardExecuteDialog
    },
    computed: {
        isNoDelete() {
            return false //this.card.executorOrder !== null || this.card.customerOrder !== null
        },
        getTimeToExecute() {
            if (this.card.customerOrder !== null && this.card.customerOrder.executeTo !== null) {
                let milliseconds = new Date(new Date(this.card.customerOrder.executeTo) - Date.now());
                let days = Math.floor((milliseconds) / (1000 * 60 * 60 * 24));
                let hours = Math.floor((milliseconds) / (1000 * 60 * 60));
                let minutes = Math.floor((milliseconds) / (1000 * 60));

                if (days >= 1)
                    return days + ' дня(ей)'
                else if (hours >= 1)
                    return hours + ' часа(ов)'
                else if (minutes >= 1)
                    return minutes + ' минут(а)'
                return ' Просрочена!'
            }
            return ''
        }
    },
    methods: {
        ...mapActions(['deleteCardFromServer']),
        deleteCard(id) {
            this.deleteCardFromServer(id)
        },
        setChangedCard(card) {
            this.changedCard = card
            this.showCreateCard = true
        }
    },

}
</script>

<style scoped>
.tooltip__text {
    margin: 0;
}
</style>