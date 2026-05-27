import apiClient from './apiClient';

interface StavkaPayload {
    sirovaZrnaId: number;
    kolicina: number;
    tipPrzenja: {
        id: number;
    };
}

interface OrderPayload {
    datumDostave: string;
    adresa: string;
    stavke: StavkaPayload[];
}

export const orderService = {
    createOrder: async (payload: OrderPayload): Promise<any> => {
        const response = await apiClient.post<any>('/narudzbina/create', payload);
        return response.data;
    },
    
    // Nova metoda za preuzimanje porudžbina ulogovanog klijenta
    getMyOrders: async (): Promise<any[]> => {
        const response = await apiClient.get<any[]>('/narudzbina/moja');
        return response.data;
    }
};