import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  // Não definimos Content-Type global para permitir multipart/form-data com boundary
})

export default api

