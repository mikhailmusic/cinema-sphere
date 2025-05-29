import axios from 'axios';

export const API_BASE_URL = import.meta.env.VITE_SPRING_API_URL;

const api = axios.create({
  baseURL: API_BASE_URL,
});

export default api;