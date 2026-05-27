import apiClient from './apiClient';
import { Korisnik } from '../types';

export const authService = {
  login: async (credentials: { email: string; password?: string }): Promise<Korisnik> => {
    const response = await apiClient.post<any>('/login', credentials);

    if (response.data && response.data.message) {
      throw new Error(response.data.message);
    }

    if (response.data.token) {
      localStorage.setItem('token', response.data.token);
    }

    return {
      username: response.data.username,
      email: credentials.email,
      uloga: response.data.uloga
    };
  },

  registerClient: async (data: { username: string; email: string; password?: string }): Promise<string> => {
    const response = await apiClient.post<any>('/register', data);

    // Checks for your neuspesno response payload
    if (response.data && response.data.status === 'failed') {
      throw new Error(response.data.message || 'Registracija nije uspela.');
    }

    // Returns "Korisnik uspešno registrovan" straight from your backend success JSON
    return response.data.message || 'Registracija uspešna!';
  }
};