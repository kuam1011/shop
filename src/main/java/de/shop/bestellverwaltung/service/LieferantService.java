package de.shop.bestellverwaltung.service;

import java.util.List;

import de.shop.bestellverwaltung.domain.Lieferant;

public interface LieferantService {
	List<Lieferant> findAllLieferanten();
	Lieferant findLieferantById(Long id);
	Lieferant createLieferant(Lieferant lieferant);
	void updateLieferant(Lieferant lieferant);
	void deleteLieferant(Long liefernantId);
}
