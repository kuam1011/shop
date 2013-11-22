package de.shop.util;

import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public class Adresse implements Serializable {
	
	
	
	private static final long serialVersionUID = -4635465770154913825L;

	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false, updatable = false)
	@XmlAttribute
	private Long id;

	@Column(name = "aktualisiert", nullable = false)
	@Temporal(TIMESTAMP)
	private Date aktualisiert;

	@Column(name = "erzeugt", nullable = false)
	@Temporal(TIMESTAMP)
	@XmlTransient
	private Date erzeugt;

	@Column(name = "strasse", nullable = false)
	@XmlElement(required = true)
	private String strasse;

	@Column(name = "hausnummer", nullable = false)
	@XmlElement(required = true)
	private String hausnummer;

	@Column(name = "plz", nullable = false)
	@XmlElement(required = true)
	private String plz;

	@Column(name = "ort", nullable = false)
	@XmlElement(required = true)
	private String ort;
	
	@Column(name = "land", nullable = false)
	@XmlElement(required = true)
	private String land;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLand() {
		return land;
	}

	public void setLand(String land) {
		this.land = land;
	}

	public Date getAktualisiert() {
		return new Date(this.aktualisiert.getTime());
	}

	public void setAktualisiert(Date aktualisiert) {
		this.aktualisiert = new Date(aktualisiert.getTime());
	}

	public Date getErzeugt() {
		return new Date(this.erzeugt.getTime());
	}

	public void setErzeugt(Date erzeugt) {
		this.erzeugt = new Date(erzeugt.getTime());
	}

	public String getHausnummer() {
		return hausnummer;
	}

	public void setHausnummer(String hausnummer) {
		this.hausnummer = hausnummer;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		//TODO Plausibilität prüfen
		this.ort = ort;
	}

	public String getPlz() {
		return plz;
	}

	public void setPlz(String plz) {
		//TODO Plausibilität prüfen
		this.plz = plz;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		//TODO Plausibilität prüfen
		this.strasse = strasse;
	}
	
	/**
	 * 
	 */
	public Adresse() {
		super();
	}

	/**
	 * @param id
	 * @param aktualisiert
	 * @param erzeugt
	 * @param strasse
	 * @param hausnummer
	 * @param plz
	 * @param ort
	 * @param land
	 */
	public Adresse(Long id, Date aktualisiert, Date erzeugt, String strasse,
			String hausnummer, String plz, String ort, String land) {
		super();
		setId(id);
		setAktualisiert(aktualisiert);
		setErzeugt(erzeugt);
		setStrasse(strasse);
		setHausnummer(hausnummer);
		setPlz(plz);
		setOrt(ort);
		setLand(land);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((aktualisiert == null) ? 0 : aktualisiert.hashCode());
		result = prime * result + ((erzeugt == null) ? 0 : erzeugt.hashCode());
		result = prime * result
				+ ((hausnummer == null) ? 0 : hausnummer.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((land == null) ? 0 : land.hashCode());
		result = prime * result + ((ort == null) ? 0 : ort.hashCode());
		result = prime * result + ((plz == null) ? 0 : plz.hashCode());
		result = prime * result + ((strasse == null) ? 0 : strasse.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adresse other = (Adresse) obj;
		if (aktualisiert == null) {
			if (other.aktualisiert != null)
				return false;
		} else if (!aktualisiert.equals(other.aktualisiert))
			return false;
		if (erzeugt == null) {
			if (other.erzeugt != null)
				return false;
		} else if (!erzeugt.equals(other.erzeugt))
			return false;
		if (hausnummer == null) {
			if (other.hausnummer != null)
				return false;
		} else if (!hausnummer.equals(other.hausnummer))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (land == null) {
			if (other.land != null)
				return false;
		} else if (!land.equals(other.land))
			return false;
		if (ort == null) {
			if (other.ort != null)
				return false;
		} else if (!ort.equals(other.ort))
			return false;
		if (plz == null) {
			if (other.plz != null)
				return false;
		} else if (!plz.equals(other.plz))
			return false;
		if (strasse == null) {
			if (other.strasse != null)
				return false;
		} else if (!strasse.equals(other.strasse))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Adresse [getId()=" + getId() + ", getLand()=" + getLand()
				+ ", getAktualisiert()=" + getAktualisiert()
				+ ", getErzeugt()=" + getErzeugt() + ", getHausnummer()="
				+ getHausnummer() + ", getOrt()=" + getOrt() + ", getPlz()="
				+ getPlz() + ", getStrasse()=" + getStrasse() + "]";
	}
}
