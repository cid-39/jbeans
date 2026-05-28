import apiClient from './apiClient';

// --- INTERFEJSI ---

export interface Dobavljac {
    pib: string;
    naziv: string;
    brojTelefona: string;
}

export interface SirovaZrna {
    id?: number;
    naziv: string;
    kolicinaNaStanju: number;
    cenaPoMeri: number;
    dobavljac: {
        pib: string;
        naziv?: string;
        brojTelefona?: string;
    };
}

// --- SERVIS ---

export const magacinService = {
    // ==========================================
    // DOBAVLJAČI
    // ==========================================

    getAllDobavljaci: async (): Promise<Dobavljac[]> => {
        const response = await apiClient.get<Dobavljac[]>('/dobavljac/getall');
        return response.data;
    },

    getDobavljac: async (pib: string): Promise<Dobavljac | null> => {
        const response = await apiClient.get<Dobavljac>(`/dobavljac/get?pib=${pib}`);
        return response.data || null;
    },

    createDobavljac: async (data: Dobavljac): Promise<Dobavljac> => {
        const response = await apiClient.post<Dobavljac>('/dobavljac/create', data);
        return response.data;
    },

    updateDobavljac: async (pib: string, data: Dobavljac): Promise<Dobavljac> => {
        const response = await apiClient.post<Dobavljac>(`/dobavljac/update?pib=${pib}`, data);
        return response.data;
    },

    removeDobavljac: async (pib: string): Promise<boolean> => {
        const response = await apiClient.post<boolean>(`/dobavljac/remove?pib=${pib}`, {});
        return response.data;
    },

    // ==========================================
    // SIROVA ZRNA
    // ==========================================

    getAllZrna: async (): Promise<SirovaZrna[]> => {
        const response = await apiClient.get<SirovaZrna[]>('/zrna/getall');
        return response.data;
    },

    getZrno: async (id: number): Promise<SirovaZrna | null> => {
        const response = await apiClient.get<SirovaZrna>(`/zrna/get?id=${id}`);
        return response.data || null;
    },

    createZrno: async (data: SirovaZrna): Promise<SirovaZrna> => {
        const response = await apiClient.post<SirovaZrna>('/zrna/create', data);
        return response.data;
    },

    updateZrno: async (id: number, data: SirovaZrna): Promise<SirovaZrna> => {
        const response = await apiClient.post<SirovaZrna>(`/zrna/update?id=${id}`, data);
        return response.data;
    },

    removeZrno: async (id: number): Promise<boolean> => {
        const response = await apiClient.post<boolean>(`/zrna/remove?id=${id}`, {});
        return response.data;
    }
};