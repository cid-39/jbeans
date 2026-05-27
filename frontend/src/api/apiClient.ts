import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
});

apiClient.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token && config.headers) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

apiClient.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response) {
            const { status } = error.response;

            // KATEGORIJA 1: 401 (Istekao token ili neautorizovan)
            // Ovde je jedino ispravno uraditi logout i brisanje sesije
            if (status === 401) {
                localStorage.removeItem('token');
                localStorage.removeItem('user');
                window.location.href = '/login';
                return Promise.reject(error);
            }

            // KATEGORIJA 2: 403 (Zabranjen pristup / Nemate administratorska prava)
            // NE izlogujemo korisnika, sesija ostaje netaknuta!
            if (status === 403) {
                console.warn("Pristup odbijen (403): Nemate adekvatne privilegije za ovaj zahtev.");
                
                // Opciono: Ako je običan korisnik pokušao da ode na /admin pa dobio 403,
                // možemo ga samo preusmeriti na početnu stranicu umesto da ga izlogujemo:
                if (window.location.pathname.startsWith('/admin')) {
                    window.location.href = '/';
                }
            }
        }
        return Promise.reject(error);
    }
);

export default apiClient;