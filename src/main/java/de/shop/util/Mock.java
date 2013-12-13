package de.shop.util;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import org.jboss.logging.Logger;

import de.shop.artikelverwaltung.domain.Fahrrad;
import de.shop.artikelverwaltung.domain.Produkt;
import de.shop.bestellverwaltung.domain.Auftrag;
import de.shop.bestellverwaltung.domain.Lieferant;
import de.shop.bestellverwaltung.domain.Rechnung;
import de.shop.bestellverwaltung.domain.Auftrag.AuftragsStatus;
import de.shop.kundenverwaltung.domain.Bankverbindung;
import de.shop.kundenverwaltung.domain.Kunde;

/**
 * Emulation des Anwendungskerns
 */
public final class Mock {
	private static final int MAX_ID = 99;
	private static final int MAX_KUNDEN = 8;
	private static final int MAX_AUFTRAEGE = 4;
	private static final int MAX_LIEFERANTEN = 8;
	private static final int MAX_RECHNUNGEN = 8;
	private static final int MAX_PRODUKTE = 32;
	private static final int JAHR = 2001;
	private static final int MONAT = 0; // bei Calendar werden die Monate von 0 bis 11 gezaehlt
	private static final int TAG = 31;  // bei Calendar die Monatstage ab 1 gezaehlt

	private static final Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass());
	
	public static Auftrag findAuftragById(Long id) {
		if (id > MAX_ID)
			return null;
		
		final Auftrag auftrag = new Auftrag();
		auftrag.setId(id);
		auftrag.setStatus(AuftragsStatus.InBearbeitung);

		System.out.println("Suche Auftrag mit id = " + id + ".");
		
		return auftrag;
	}
	
	public static Lieferant findLieferantById(Long id) {
		if (id > MAX_ID)
			return null;
		
		final Lieferant lieferant = new Lieferant();
		lieferant.setId(id);
		final int lieferzeitTage = 3;
		lieferant.setLieferzeit(lieferzeitTage);
		lieferant.setName("Breisinger Nr." + id);
		final Adresse adresse = new Adresse();
		adresse.setId(id + 1);
		adresse.setAktualisiert(new Date());
		adresse.setErzeugt(new Date());
		adresse.setStrasse("Musterallee");
		adresse.setHausnummer("101");
		adresse.setPlz("76327");
		adresse.setOrt("Pfinztal");
		adresse.setLand("Deutschland");
		lieferant.setAdresse(adresse);
		
		System.out.println("Suche Lieferant mit id = " + id + ".");
		
		return lieferant;
	}
	
	public static Rechnung findRechnungById(Long id) {
		if (id > MAX_ID)
			return null;
		
		final Rechnung rechnung = new Rechnung();
		rechnung.setId(id);
		rechnung.setIstBezahlt(false);
		final BigDecimal summeRechnung = BigDecimal.valueOf(123.45);
		rechnung.setSumme(summeRechnung);

		System.out.println("Suche Rechnung mit id = " + id + ".");
		
		return rechnung;
	}
	
	public static List<Rechnung> findRechnungenByAuftrag(Auftrag auftrag) {

		System.out.println("Suche alle Rechnungen des Auftrags: " + auftrag + ".");
		
		return findAllRechnungen();
	}
	
	public static List<Lieferant> findLieferantenByAuftrag(Auftrag auftrag) {
		
		System.out.println("Suche alle Lieferanten des Auftrags: " + auftrag + ".");
		
		return findAllLieferanten();
	}
	
	public static Kunde findKundeById(Long id) {
		if (id > MAX_ID)
			return null;
		
		final Kunde kunde = new Kunde();
		kunde.setId(id);
		kunde.setNachname("Mustermann");
		kunde.setVorname("Max");
		kunde.setAnrede("Herr");
		kunde.seteMail("test@mail.com");
		kunde.setTelefon("072112345");
		final GregorianCalendar seitCal = new GregorianCalendar(JAHR, MONAT, TAG);
		final Date seit = seitCal.getTime();
		kunde.setSeit(seit);
		final Adresse adresse = new Adresse();
		adresse.setId(id + 1);
		adresse.setAktualisiert(new Date());
		adresse.setErzeugt(new Date());
		adresse.setStrasse("Musterallee");
		adresse.setHausnummer("101");
		adresse.setPlz("76327");
		adresse.setOrt("Pfinztal");
		adresse.setLand("Deutschland");
		kunde.setAdresse(adresse);

		//System.out.println("Suche Kunde mit id = " + id + ".");
		
		return kunde;
	}
	
	public static List<Kunde> findAllKunden() {
		final int anzahl = MAX_KUNDEN;
		final List<Kunde> kunden = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			final Kunde kunde = findKundeById(Long.valueOf(i));
			kunden.add(kunde);			
		}

		System.out.println("Suche alle Kunden.");
		
		return kunden;
	}
	
	public static List<Kunde> findKundenByNachname(String nachname) {
		final int anzahl = nachname.length();
		final List<Kunde> kunden = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			final Kunde kunde = findKundeById(Long.valueOf(i));
			kunde.setNachname(nachname);
			kunden.add(kunde);			
		}

		System.out.println("Suche alle Kunden mit Nachnamen: " + nachname + ".");
		
		return kunden;
	}
	
	public static Kunde findKundeByEmail(String email) {
		if (email.startsWith("x")) {
			return null;
		}
		// TODO Stimmt das??
		// Original Zimmermann:
		// final AbstractKunde kunde = email.length() % 2 == 1 ? new Privatkunde() : new Firmenkunde();
		final Kunde kunde = new Kunde();
		kunde.setId(Long.valueOf(email.length()));
		kunde.setNachname("Nachname");
		kunde.seteMail(email);
		final GregorianCalendar seitCal = new GregorianCalendar(JAHR, MONAT, TAG);
		final Date seit = seitCal.getTime();
		kunde.setSeit(seit);
		
		final Adresse adresse = new Adresse();
		adresse.setId(kunde.getId() + 1);        // andere ID fuer die Adresse
		adresse.setPlz("12345");
		adresse.setOrt("Testort");
		adresse.setKunde(kunde);
		kunde.setAdresse(adresse);
		
		
		return kunde;
	}
	
	public static List<Auftrag> findAllAuftraege() {
		final int anzahl = MAX_AUFTRAEGE;
		final List<Auftrag> auftraege = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			final Auftrag auftrag = findAuftragById(Long.valueOf(i));
			auftraege.add(auftrag);
		}

		System.out.println("Suche alle Auftraege.");
		
		return auftraege;
	}
	
	public static List<Lieferant> findAllLieferanten() {
		final int anzahl = MAX_LIEFERANTEN;
		final List<Lieferant> lieferanten = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			final Lieferant lieferant = findLieferantById(Long.valueOf(i));
			lieferanten.add(lieferant);
		}
		
		System.out.println("Suche alle Lieferanten.");
		
		return lieferanten;
	}
	
	public static List<Rechnung> findAllRechnungen() {
		final int anzahl = MAX_RECHNUNGEN;
		final List<Rechnung> rechnungen = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			final Rechnung rechnung = findRechnungById(Long.valueOf(i));
			rechnungen.add(rechnung);
		}
		
		System.out.println("Suche alle Rechnungen.");
		
		return rechnungen;
	}
	
	public static Auftrag createAuftrag(Auftrag auftrag) {
		final Random randomGenerator = new Random();
		auftrag.setId(randomGenerator.nextLong());
		
		System.out.println("Neuer Aufrag: " + auftrag);
		
		return auftrag;
	}
	
	public static <T extends Kunde> T createKunde(T kunde) {
		/* Neue IDs fuer Kunde und zugehoerige Adresse
		 * Ein neuer Kunde hat auch keine Bestellungen
		 * Vorberietung für entfernen von Mock
		 * 
		final String vorname = kunde.getVorname();
		final Adresse adresse = kunde.getAdresse();
		adresse.setId((Long.valueOf(nachname.length())) + 1);
		*/
		
		final String nachname = kunde.getNachname();
		kunde.setId(Long.valueOf(nachname.length()));
		kunde.setNachname("Mustermann_created");
		kunde.setVorname("MaxC");
		kunde.setAnrede("Herr");
		kunde.seteMail("createdtest@mail.com");
		kunde.setTelefon("072112345");
		kunde.setBestellungen(null);
		final Adresse adresse = new Adresse();
		final long id = 5;
		adresse.setId(id);
		adresse.setAktualisiert(new Date());
		adresse.setErzeugt(new Date());
		adresse.setStrasse("Musterallee");
		adresse.setHausnummer("101");
		adresse.setPlz("76327");
		adresse.setOrt("Pfinztal");
		adresse.setLand("Deutschland");
		kunde.setAdresse(adresse);
		kunde.setBankverbindung(new Bankverbindung(id, "123456789", "20040010", "Bank of Abzocking"));
		
		LOGGER.infof("Neuer Kunde: %s", kunde);
		
		return kunde;
	}
	
	public static Lieferant createLieferant(Lieferant lieferant) {
		final Random randomGenerator = new Random();
		final Long id = randomGenerator.nextLong();
		lieferant.setId(id);
		final int lieferzeitTage = 3;
		lieferant.setLieferzeit(lieferzeitTage);
		lieferant.setName("Breisinger Nr." + id);
		final Adresse adresse = new Adresse();
		adresse.setId(id + 1);
		adresse.setAktualisiert(new Date());
		adresse.setErzeugt(new Date());
		adresse.setStrasse("Musterallee");
		adresse.setHausnummer("101");
		adresse.setPlz("76327");
		adresse.setOrt("Pfinztal");
		adresse.setLand("Deutschland");
		lieferant.setAdresse(adresse);

		System.out.println("Neuer Lieferant: " + lieferant);
		
		return lieferant;
	}
	
	public static Lieferant createLieferantInAuftrag(Long auftragId, Lieferant lieferant) {
		final Random randomGenerator = new Random();
		final long id = randomGenerator.nextLong();
		lieferant.setId(id);
		final int lieferzeitTage = 3;
		lieferant.setLieferzeit(lieferzeitTage);
		lieferant.setName("Breisinger Nr." + id);
		final Adresse adresse = new Adresse();
		adresse.setId(id + 1);
		adresse.setAktualisiert(new Date());
		adresse.setErzeugt(new Date());
		adresse.setStrasse("Musterallee");
		adresse.setHausnummer("101");
		adresse.setPlz("76327");
		adresse.setOrt("Pfinztal");
		adresse.setLand("Deutschland");
		lieferant.setAdresse(adresse);
		
		final Auftrag auftrag = findAuftragById(auftragId);
		
		System.out.println("Neuer Lieferant: " + lieferant + " in Auftrag: " + auftrag);
		
		return lieferant;
	}
	
	public static Rechnung createRechnung(Rechnung rechnung) {
		final Random randomGenerator = new Random();
		final Long id = randomGenerator.nextLong();
		rechnung.setId(id);
		rechnung.setIstBezahlt(false);
		final BigDecimal summe = BigDecimal.valueOf(123.45);
		rechnung.setSumme(summe);
		
		System.out.println("Neue Rechnung. " + rechnung);
		
		return rechnung;
	}
	
	public static Rechnung createRechnungInAuftrag(Long auftragId, Rechnung rechnung) {
		final Random randomGenerator = new Random();
		final Long id = randomGenerator.nextLong();
		rechnung.setId(id);
		rechnung.setIstBezahlt(false);
		final BigDecimal summe = BigDecimal.valueOf(123.45);
		rechnung.setSumme(summe);
		
		final Auftrag auftrag = findAuftragById(auftragId);
		
		System.out.println("Neue Rechnung. " + rechnung + " in Auftrag: " + auftrag);
		
		return rechnung;
	}
	
	public static void updateAuftrag(Auftrag auftrag) {
		System.out.println("Aktualisierter Auftrag: " + auftrag);
	}

	public static void updateKunde(Kunde kunde) {
		LOGGER.infof("Aktualisierter Kunde: %s", kunde);
	}
	
	public static void updateLieferant(Lieferant lieferant) {
		System.out.println("Aktualisierter Lieferant: " + lieferant);
	}
	
	public static void updateRechnung(Rechnung rechnung) {
		System.out.println("Aktualisierte Rechnung: " + rechnung);
	}
	
	public static void deleteAuftrag(Long id) {
		System.out.println("Auftrag mit ID " + id + " geloescht.");
	}

	public static void deleteKunde(Kunde kunde) {
		LOGGER.infof("Geloeschter Kunde: %s", kunde);
	}
	
	public static void deleteLieferant(Long id) {
		System.out.println("Lieferant mit ID=" + id + " geloescht.");
	}
	
	public static void deleteRechnung(Long id) {
		System.out.println("Rechnung mit ID=" + id + " geloescht.");
	}
	
	public static Produkt findProduktById(Long id) {
		
		final Produkt produkt = new Produkt();
		
		produkt.setid(id);
		produkt.setBezeichnung("Bezeichnung_" + id + "_Mock");
		return produkt;
	}
	
	public static Produkt createProdukt(Produkt produkt) {
		final Random randomGenerator = new Random();
		final Long id = randomGenerator.nextLong();
		
		produkt.setid(id);
		produkt.setProduktnummer("12345678");
		final BigDecimal preis = BigDecimal.valueOf(0.99);
		produkt.setPreis(preis);
		produkt.setBezeichnung("Testbezeichnung");
		final BigDecimal rabatt = BigDecimal.valueOf(20.00);
		produkt.setRabatt(rabatt);
		//produkt.setEndpreis(produkt.endPreisBerechnen(produkt.getPreis(), produkt.getRabatt()));		
		
		System.out.println("Neues Produkt: " + produkt);
		
		return produkt;
	}
	
	public static void updateProdukt(Produkt produkt) {
		System.out.println("Aktualisiertes Produkt: " + produkt);
		
	}

	public static void deleteProdukt(Long produktId) {
		System.out.println("Produkt mit ID=" + produktId + " geloescht.");
		
	}

	public static List<Produkt> findAllProdukte() {
		final int anzahl = MAX_PRODUKTE;
		final List<Produkt> produkte = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			final Produkt produkt = findProduktById(Long.valueOf(i));
			produkte.add(produkt);
		}
		
		System.out.println("Suche alle Produkte.");
		
		return produkte;
	}
	
	public static Fahrrad findFahrradById(Long id) {
		
		final Fahrrad rad = new Fahrrad();
		
		rad.setid(id);
		rad.setBezeichnung("Bezeichnung_" + id + "_Mock");
		return rad;
	}
	
	public static Fahrrad createFahrrad(Fahrrad rad) {
		final Random randomGenerator = new Random();
		final Long id = randomGenerator.nextLong();
		
		rad.setid(id);
		rad.setProduktnummer("12345678");
		final BigDecimal preis = BigDecimal.valueOf(0.99);
		rad.setPreis(preis);
		rad.setBezeichnung("Testbezeichnung");
		final BigDecimal rabatt = BigDecimal.valueOf(20.00);
		rad.setRabatt(rabatt);
		//rad.setEndpreis(rad.endPreisBerechnen(rad.getPreis(), rad.getRabatt()));		
		
		System.out.println("Neues Fahrrad: " + rad);
		
		return rad;
	}
	public static void updateFahrrad(Fahrrad rad) {
		System.out.println("Aktualisiertes Produkt: " + rad);
		
	}

	public static void deleteFahrrad(Long radId) {
		System.out.println("Produkt mit ID=" + radId + " geloescht.");
		
	}

	public static List<Fahrrad> findAllFahrraeder() {
		final int anzahl = MAX_PRODUKTE;
		final List<Fahrrad> raeder = new ArrayList<>(anzahl);
		for (int i = 1; i <= anzahl; i++) {
			final Fahrrad rad = findFahrradById(Long.valueOf(i));
			raeder.add(rad);
		}
		
		System.out.println("Suche alle Produkte.");
		
		return raeder;
	}
	private Mock() { /**/ }
}
