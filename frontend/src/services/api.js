import axios from 'axios'

const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  // Do not set a global Content-Type header so the browser can
  // set the correct multipart/form-data boundary when sending FormData.
})

export default api
