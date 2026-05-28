import apiClient from './apiClient';
import { Proizvod } from './proizvodService';

// --- INTERFEJSI ---

export interface Klijent {
    id: number;
    username: string;
    email: string;
    adresa: string;
}

export interface Dostavljanje {
    id: number;
    datumDostave: string;
    status: string;
}

export interface Stavka {
    id: number;
    proizvod: Proizvod;
    cena: number;
    kolicina: number;
}

export interface Narudzbina {
    id: number;
    datumPorucivanja: string;
    ukupnaCena: number;
    klijent: Klijent;
    dostavljanje: Dostavljanje;
    pretplata: boolean;
    stavke: Stavka[];
}

export interface Pretplata {
    id: number;
    period: number;
    aktivna: boolean;
    narudzbine: Narudzbina[];
}

// --- SERVISI ---

export const narudzbinaService = {
    getAllNarudzbine: async (): Promise<Narudzbina[]> => {
        const response = await apiClient.get<Narudzbina[]>('/narudzbina/getall');
        return response.data || [];
    },

    getNarudzbina: async (id: number): Promise<Narudzbina | null> => {
        const response = await apiClient.get<Narudzbina>(`/narudzbina/get?id=${id}`);
        return response.data || null;
    }
};

export const pretplataService = {
    getAllPretplate: async (): Promise<Pretplata[]> => {
        const response = await apiClient.get<Pretplata[]>('/pretplata/getall');
        return response.data || [];
    }
};