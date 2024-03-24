import axios from "axios";

export default {
    saveCard : card => axios.post("/api/cards", card),

    executeCard : card => axios.post("/api/cards/execute", card, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    }),

    getCards : () => axios.get("/api/cards"),

    getDeletedCards : () => axios.get("/api/cards/deleted"),

    getCardById : id => axios.get(`/api/cards/${id}`),

    updateCard : card => axios.put("/api/cards", card, {
        headers: {
            'Content-Type': 'multipart/form-data'
        }
    }),

    deleteCard : id => axios.delete(`/api/cards/${id}`)
}