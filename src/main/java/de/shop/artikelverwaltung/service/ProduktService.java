package de.shop.artikelverwaltung.service;

import java.util.List;

import de.shop.artikelverwaltung.domain.Produkt;
import de.shop.util.interceptor.Log;



@Log
public interface ProduktService {
		Produkt findProduktById(Long id);
		Produkt createProdukt(Produkt produkt);
		List<Produkt> findAllProdukte();
		void updateProdukt(Produkt produkt);
		void deleteProdukt(Long produktId);
		
}
