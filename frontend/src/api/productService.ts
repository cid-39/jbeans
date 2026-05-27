import apiClient from './apiClient';
import { SirovaZrna } from '../types';

export const productService = {
  getAllZrna: async (): Promise<SirovaZrna[]> => {
    const response = await apiClient.get<SirovaZrna[]>('/zrna/getall');
    return response.data;
  }
};