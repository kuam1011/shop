package de.shop.bestellverwaltung.service;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Dependent;

import de.shop.bestellverwaltung.domain.Auftrag;
import de.shop.bestellverwaltung.domain.Lieferant;
import de.shop.bestellverwaltung.domain.Rechnung;
import de.shop.util.Mock;
import de.shop.util.interceptor.Log;

@Dependent
@Log
public class AuftragServiceLogImplementierung implements Serializable, AuftragService {
	private static final long serialVersionUID = 7801221443939357632L;
	//private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	
	@Override
	public Auftrag findAuftragById(Long id) {
		return Mock.findAuftragById(id);
	}
	
	@Override
	public List<Auftrag> findAllAuftraege() {
		//LOGGER.debugf("BEGIN findAllAuftraege");
		return Mock.findAllAuftraege();
	}

	@Override
	public List<Rechnung> findRechnungenByAuftragId(Long auftragId) {
		return Mock.findRechnungenByAuftrag(Mock.findAuftragById(auftragId));
	}

	@Override
	public List<Lieferant> findLieferantenByAuftragId(Long auftragId) {
		return Mock.findLieferantenByAuftrag(Mock.findAuftragById(auftragId));
	}

	@Override
	public Lieferant createLieferant(Long auftragId, Lieferant lieferant) {
		return Mock.createLieferantInAuftrag(auftragId, lieferant);
	}

	@Override
	public Rechnung createRechnung(Long auftragId, Rechnung rechnung) {
		return Mock.createRechnungInAuftrag(auftragId, rechnung);
	}

	@Override
	public Auftrag createAuftrag(Auftrag auftrag) {
		return auftrag;
	}

	@Override
	public void updateAuftrag(Auftrag auftrag) {
		Mock.updateAuftrag(auftrag);
	}

	@Override
	public void deleteAuftrag(Long auftragId) {
		Mock.deleteAuftrag(auftragId);
	}
}
