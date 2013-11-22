package de.shop.bestellverwaltung.domain;

public class LieferantException extends Exception {
	
	private static final long serialVersionUID = -8888165622170465585L;
	
	/**
	 * @param beschreibung Exceptionbeschreibung.
	 */
	public LieferantException(String beschreibung) {
		super(beschreibung);
	}
}
