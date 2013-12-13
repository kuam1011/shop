package de.shop.bestellverwaltung.service;

import java.util.List;

import de.shop.bestellverwaltung.domain.Auftrag;
import de.shop.bestellverwaltung.domain.Lieferant;
import de.shop.bestellverwaltung.domain.Rechnung;

public interface AuftragService {
	Auftrag findAuftragById(Long id);
	List<Auftrag> findAllAuftraege();
	List<Rechnung> findRechnungenByAuftragId(Long auftragId);
	List<Lieferant> findLieferantenByAuftragId(Long auftragId);
	Lieferant createLieferant(Long auftragId, Lieferant lieferant);
	Rechnung createRechnung(Long auftragId, Rechnung rechnung);
	Auftrag createAuftrag(Auftrag auftrag);
	void updateAuftrag(Auftrag auftrag);
	void deleteAuftrag(Long auftragId);
}
