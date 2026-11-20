import axios from 'axios'

const api = axios.create({
    baseURL: 'http://localhost:8080/api/fuzzyrelation',
    headers: {
        'Content-Type': 'application/json'
    }
})

export async function calculateRelationImage(request) {
    const response = await api.post('/calculate/img', request)
    return response.data
}

export async function calculateComposition(request) {
    const response = await api.post('/calculate/compose', request)
    return response.data
}