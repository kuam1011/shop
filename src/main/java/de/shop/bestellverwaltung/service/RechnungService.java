package de.shop.bestellverwaltung.service;

import java.util.List;

import de.shop.bestellverwaltung.domain.Rechnung;

public interface RechnungService {
	List<Rechnung> findAllRechnungen();
	Rechnung findRechnungById(Long id);
	Rechnung createRechnung(Rechnung rechnung);
	void updateRechnung(Rechnung rechnung);
	void deleteRechnung(Long liefernantId);
}
