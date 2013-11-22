package de.shop.bestellverwaltung.domain;

public class RechnungException extends Exception {

	private static final long serialVersionUID = -157123077582469332L;

	/**
	 * @param beschreibung Exceptionbeschreibung.
	 */
	public RechnungException(String beschreibung) {
		super(beschreibung);
	}
}
