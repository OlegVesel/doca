import axios from "axios";

export default {
    getAllUsers : () => axios.get("/api/users/short"),
}