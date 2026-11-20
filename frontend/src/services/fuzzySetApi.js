import axios from 'axios'

const api = axios.create({
    baseURL: "http://localhost:8080/api/fuzzyset",
    headers: {
        "Content-type": "application/json"
    }
})

export async function calculateOperation(request){
    const response = await api.post("/calculate", request);
    return response.data;
}