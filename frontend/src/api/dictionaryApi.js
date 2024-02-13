import axios from "axios";

export default {
    getAllTypeDocs : () => axios.get("/api/dictionary/type_docs")
}