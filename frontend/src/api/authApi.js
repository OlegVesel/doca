import axios from "axios";

export default {
    async authorize(account) {
        return  await axios.post("/api/auth/login", account)
    },
}