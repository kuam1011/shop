package de.shop.kundenverwaltung.service;

import javax.ejb.ApplicationException;

import de.shop.kundenverwaltung.domain.Kunde;


@ApplicationException(rollback = true)
public class KundeDeleteAuftraegeException extends AbstractKundeServiceException {
	
	
	private static final long serialVersionUID = 1411648988418231109L;
	private static final String MESSAGE_KEY = "kunde.deleteMitBestellung";
	private final Long kundeId;
	private final int anzahlBestellungen;
	
	public KundeDeleteAuftraegeException(Kunde kunde) {
		super("Kunde mit ID=" + kunde.getId() + " kann nicht geloescht werden: "
			  + kunde.getBestellungen().size() + " Bestellung(en)");
		this.kundeId = kunde.getId();
		this.anzahlBestellungen = kunde.getBestellungen().size();
	}

	public Long getKundeId() {
		return kundeId;
	}
	public int getAnzahlBestellungen() {
		return anzahlBestellungen;
	}
	
	@Override
	public String getMessageKey() {
		return MESSAGE_KEY;
	}
}

