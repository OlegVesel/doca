import axios from "axios";

export default {
    authorize : (account) => axios.post("/auth/login", account),
    registration : (user) => axios.post("/auth/registration", user),

}