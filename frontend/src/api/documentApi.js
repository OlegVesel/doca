import axios from "axios";

export default {
    softDeleteDocument : (id) => axios.delete(`/api/documents/${id}`),

    hardDeleteDocument : (id) => axios.delete(`/api/documents/${id}/hard`)
}