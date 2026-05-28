import apiClient from './apiClient';

export interface TipVozila {
    id: number;
    naziv: string;
}

export interface Vozilo {
    registracija: string;
    tipVozila: TipVozila | null;
}

export interface Dostava {
    id: number;
    vozilo: Vozilo | null;
    datumDostave: string;
    status: string;
}

export const dostavaService = {
    getAllDostave: async (): Promise<Dostava[]> => {
        const response = await apiClient.get<Dostava[]>('/dostava/getall');
        return response.data || [];
    },

    getAllVozila: async (): Promise<Vozilo[]> => {
        const response = await apiClient.get<Vozilo[]>('/vozilo/getall');
        return response.data || [];
    },

    dodeliVozilo: async (id: number, registracija: string): Promise<Dostava> => {
        // Ruta prima id kroz query parametar, a registraciju kroz body objekt
        const response = await apiClient.post<Dostava>(`/dostava/vozilo?id=${id}`, { registracija });
        return response.data;
    },

    updateStatus: async (id: number, ishod: string): Promise<Dostava> => {
        const response = await apiClient.post<Dostava>(`/dostava/update?id=${id}&ishod=${ishod}`, {});
        return response.data;
    }
};