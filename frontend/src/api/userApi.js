import axios from "axios";

export default {
    getAllUsers : () => axios.get("/api/users/short"),
    getUserByLogin : login => axios.get(`/api/users/${login}`),
    updateUser: user => axios.put('/api/users', user)
}