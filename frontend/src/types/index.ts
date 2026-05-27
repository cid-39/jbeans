export interface Korisnik {
  id?: number;
  username: string;
  email: string;
  password?: string;
  uloga?: 'KORISNIK' | 'ADMIN'; // Frontend fallback guard mapping
}

export interface TipPrzenja {
  id: number;
  naziv: string;
}

export interface SirovaZrna {
  id: number;
  naziv: string;
  kolicinaNaStanju: number; // camelCase conversion from Jackson
  cenaPoMeri: number;        // Price per unit (e.g., per kg)
  dobavljac?: any;           // Ignored for now as requested
}

export interface Proizvod {
  id: number;
  naziv: string;
  opis?: string;
  kolicinaPrzena: number; // camelCase conversion from kolicina_przena
  tipPrzenja: TipPrzenja;
  zrna: SirovaZrna;
}

export interface StavkaNarudzbine {
  id?: number;
  proizvod: Proizvod; // Maps to your @ManyToOne Proizvod relation
  cena: number;
  kolicina: number; // Kept as number (maps to Double on backend)
}

export interface Dostavljanje {
  id: number;
  [key: string]: any; // Catch-all structure for delivery status metadata if used
}

export interface Klijent extends Korisnik {
  // Inherits id, username, email, password from Korisnik due to JOINED table inheritance
  [key: string]: any;
}

export interface Narudzbina {
  id?: number;
  datumPorucivanja?: string; // ISO timestamp string from java.time.LocalDateTime
  ukupnaCena: number;
  klijent?: Klijent;
  dostavljanje?: Dostavljanje;
  stavke: StavkaNarudzbine[]; // Core array composition
  pretplata: boolean;
}

export interface Pretplata {
  id?: number;
  period: number; // Maps to Integer period (days interval)
  narudzbina: Narudzbina; // Core 1-to-1 blueprint mapping
}

// Frontend utility tracker for local memory state management
export interface BasketItem {
  proizvod: Proizvod;
  kolicina: number;
}