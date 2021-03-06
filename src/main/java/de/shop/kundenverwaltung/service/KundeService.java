package de.shop.kundenverwaltung.service;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import de.shop.kundenverwaltung.domain.Kunde;
import de.shop.util.Mock;
import de.shop.util.interceptor.Log;

@Dependent
@Log
public class KundeService implements Serializable{

	
	private static final long serialVersionUID = 4360325837484294309L;
	
	@NotNull(message = "{kunde.notFound.id}")
	public Kunde findKundeById(Long id) {
		if (id == null) {
			return null;
		}
		// TODO Datenbanzugriffsschicht statt Mock
		return Mock.findKundeById(id);
	}
	
	@NotNull(message = "{kunde.notFound.email}")
	public Kunde findKundeByEmail(String email) {
		if (email == null) {
			return null;
		}
		// TODO Datenbanzugriffsschicht statt Mock
		return Mock.findKundeByEmail(email);
	}
	
	public List<Kunde> findAllKunden() {
		// TODO Datenbanzugriffsschicht statt Mock
		return Mock.findAllKunden();
	}
	
	@Size(min = 1, message = "{kunde.notFound.nachname}")
	public List<Kunde> findKundenByNachname(String nachname) {
		// TODO Datenbanzugriffsschicht statt Mock
		return Mock.findKundenByNachname(nachname);
	}

	public <T extends Kunde> T createKunde(T kunde) {
		if (kunde == null) {
			return kunde;
		}

		//final Kunde tmp = findKundeByEmail(kunde.geteMail());  // Kein Aufruf als Business-Methode
		//if (tmp != null) {
		//	throw new EmailExistsException(kunde.geteMail());
		//}
		
		// TODO Datenbanzugriffsschicht statt Mock
		kunde = Mock.createKunde(kunde);

		return kunde;
	}
	
	public <T extends Kunde> T updateKunde(T kunde) {
		if (kunde == null) {
			return null;
		}

		// Pruefung, ob die Email-Adresse schon existiert
		//final Kunde vorhandenerKunde = findKundeByEmail(kunde.geteMail());  // Kein Aufruf als Business-Methode
		//if (vorhandenerKunde != null) {
			// Gibt es die Email-Adresse bei einem anderen, bereits vorhandenen Kunden?
		//	if (vorhandenerKunde.getId().longValue() != kunde.getId().longValue()) {
		//		throw new EmailExistsException(kunde.geteMail());
		//	}
		//}

		// TODO Datenbanzugriffsschicht statt Mock
		Mock.updateKunde(kunde);
		
		return kunde;
	}

	public void deleteKunde(Long kundeId) {
		Kunde kunde = findKundeById(kundeId);  // Kein Aufruf als Business-Methode
		if (kunde == null) {
			return;
		}

		// Gibt es Bestellungen?
		//if (!kunde.getBestellungen().isEmpty()) {
		//	throw new KundeDeleteAuftraegeException(kunde);
		//}
		
		// TODO Datenbanzugriffsschicht statt Mock
		Mock.deleteKunde(kunde);
	}

}
