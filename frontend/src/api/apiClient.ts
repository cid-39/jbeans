import axios from 'axios';

const apiClient = axios.create({
  baseURL: 'http://localhost:8080',
  headers: {
    'Content-Type': 'application/json',
  },
});

// Pomoćna funkcija koja kreira i prikazuje ulepšani modal direktno na ekranu
const prikaziGreskaModal = () => {
    // Proveravamo da li već postoji otvoren modal na ekranu da ih ne bismo duplirali
    if (document.getElementById('global-error-modal')) return;

    // Kreiramo overlay (tamnu pozadinu)
    const overlay = document.createElement('div');
    overlay.id = 'global-error-modal';
    overlay.style.position = 'fixed';
    overlay.style.top = '0';
    overlay.style.left = '0';
    overlay.style.width = '100%';
    overlay.style.height = '100%';
    overlay.style.background = 'rgba(0, 0, 0, 0.5)';
    overlay.style.display = 'flex';
    overlay.style.justifyContent = 'center';
    overlay.style.alignItems = 'center';
    overlay.style.zIndex = '9999'; // Osiguravamo da je uvek na vrhu

    // Kreiramo kontejner modala (beli prozor) koji prati stil tvoje aplikacije
    const modal = document.createElement('div');
    modal.style.background = '#fff';
    modal.style.padding = '30px';
    modal.style.borderRadius = '8px';
    modal.style.width = '350px';
    modal.style.textAlign = 'center';
    modal.style.boxShadow = '0 4px 15px rgba(0,0,0,0.2)';

    // Naslov modala (Crveni tekst za grešku)
    const naslov = document.createElement('h3');
    naslov.innerText = 'Greška!';
    naslov.style.margin = '0 0 15px 0';
    naslov.style.color = '#dc3545';
    naslov.style.fontSize = '22px';

    // Tekst poruke
    const poruka = document.createElement('p');
    poruka.innerText = 'Nije moguće izvršiti ovu akciju.';
    poruka.style.margin = '0 0 25px 0';
    poruka.style.color = '#333';
    poruka.style.fontSize = '15px';

    // Dugme za zatvaranje (sivo/neutralno dugme)
    const dugme = document.createElement('button');
    dugme.innerText = 'Zatvori';
    dugme.style.padding = '8px 25px';
    dugme.style.background = '#6c757d';
    dugme.style.color = '#fff';
    dugme.style.border = 'none';
    dugme.style.borderRadius = '4px';
    dugme.style.cursor = 'pointer';
    dugme.style.fontWeight = 'bold';
    
    // Klikom na dugme uklanjamo ceo overlay iz DOM-a
    dugme.onclick = () => {
        document.body.removeChild(overlay);
    };

    // Sklapanje modala
    modal.appendChild(naslov);
    modal.appendChild(poruka);
    modal.appendChild(dugme);
    overlay.appendChild(modal);

    // Ubacivanje u body aplikacije
    document.body.appendChild(overlay);
};

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
        const { response, config } = error;

        if (response) {
            const { status } = response;

            // KATEGORIJA 1: 401 (Istekao token)
            if (status === 401) {
                localStorage.removeItem('token');
                localStorage.removeItem('user');
                window.location.href = '/login';
                return Promise.reject(error);
            }

            // KATEGORIJA 2: 403 (Zabranjen pristup / Validaciona greška)
            if (status === 403) {
                console.warn("Pristup odbijen (403): Zahtev nije prošao validaciju.");
                
                // --- OVDE OKIDAMO NAŠ GLOBALNI MODAL ---
                prikaziGreskaModal();
                // ----------------------------------------

                if (config && config.method === 'get' && window.location.pathname.startsWith('/admin')) {
                    window.location.href = '/';
                }
            }
        }
        return Promise.reject(error);
    }
);

export default apiClient;