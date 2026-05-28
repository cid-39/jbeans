import apiClient from './apiClient';
import { SirovaZrna } from './magacinService'; // Ponovo koristimo interfejs za zrna

export interface TipPrzenja {
    id: number;
    naziv: string;
}

export interface Proizvod {
    id: number;
    datumPrzenja: string;
    opis: string;
    kolicinaPrzena: number;
    tipPrzenja: TipPrzenja;
    zrna: SirovaZrna;
}

export interface DnevniPlan {
    nazivZrna: string;
    tipPrzenja: string;
    kolicina: number;
    opis: string;
}

export const proizvodService = {
    getAllProizvodi: async (): Promise<Proizvod[]> => {
        const response = await apiClient.get<Proizvod[]>('/proizvod/getall');
        return response.data || [];
    },

    getProizvod: async (id: number): Promise<Proizvod | null> => {
        const response = await apiClient.get<Proizvod>(`/proizvod/get?id=${id}`);
        return response.data || null;
    },

    potvrdiPrzenje: async (id: number): Promise<Proizvod> => {
        // Ruta: /proizvod/update?id=6&status=ISPRZENO
        const response = await apiClient.post<Proizvod>(`/proizvod/update?id=${id}&status=ISPRZENO`, {});
        return response.data;
    },
    
    getDnevniSpisak: async (): Promise<DnevniPlan[]> => {
        const response = await apiClient.get<DnevniPlan[]>('/dnevni_spisak');
        return response.data || [];
    }
};