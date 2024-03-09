import axios from "axios";

export default {
    assignExecutor : order => axios.post("/api/order", order),
}