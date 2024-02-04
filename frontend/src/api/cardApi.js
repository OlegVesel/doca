import axios from "axios";

export default {
    saveCard : card => axios.post("/api/cards", card),

    getCards : () => axios.get("/api/cards")
}