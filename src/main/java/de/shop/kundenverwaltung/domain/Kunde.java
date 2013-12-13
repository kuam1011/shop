package de.shop.kundenverwaltung.domain;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonTypeInfo;

import de.shop.util.Adresse;
import de.shop.bestellverwaltung.domain.Auftrag;

@XmlRootElement
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public class Kunde implements Serializable {

	private static final long serialVersionUID = -423737514968775456L;
	
	private static final int NACHNAME_LENGTH_MIN = 2;
	private static final int NACHNAME_LENGTH_MAX = 32;
	private static final int VORNAME_LENGTH_MIN = 2;
	private static final int VORNAME_LENGTH_MAX = 20;
	private static final int EMAIL_LENGTH_MAX = 128;
	
	private URI bestellungenUri;

	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@NotNull(message="{kunde.anrede.notNull}")
	private String anrede;

	@NotNull(message = "{kunde.email.notNull}")
	@Size(max = EMAIL_LENGTH_MAX, message = "{kunde.email.length}")
	//@Email(message = "{kunde.email.pattern}")
	// TODO: @Email oder @Pattern ??
	@Pattern(regexp = "[\\w.%-]+@[\\w.%-]+\\.[A-Za-z]{2,4}", message="{kunde.email.pattern}")
	private String eMail;

	@NotNull(message = "{kunde.nachname.notNull}")
	@Size(min = NACHNAME_LENGTH_MIN, max = NACHNAME_LENGTH_MAX, message = "{kunde.nachname.length}")
	private String nachname;

	@NotNull(message = "{kunde.vorname.notNull}")
	@Size(min = VORNAME_LENGTH_MIN, max = VORNAME_LENGTH_MAX, message = "{kunde.vorname.length}")
	private String vorname;
	
	private String telefon;

	@Valid
	@NotNull(message = "{kunde.adresse.notNull}")
	private Adresse adressekunde;
	
	@Valid
	private Bankverbindung bankverbindung;

	public void setValues(Kunde k) {
		nachname = k.nachname;
		vorname = k.vorname;
		eMail = k.eMail;
	}
	
	@Past(message = "{kunde.seit.past}")
	private Date seit;
	
	@XmlTransient
	private List<Auftrag> auftraege;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAnrede() {
		return this.anrede;
	}

	public void setAnrede(String anrede) {
		this.anrede = anrede;
	}

	public String getNachname() {
		return this.nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public String getTelefon() {
		return this.telefon;
	}

	public void setTelefon(String telefon) {
		this.telefon = telefon;
	}

	public String getVorname() {
		return this.vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public Adresse getAdresse() {
		return adressekunde;
	}

	public void setAdresse(Adresse adresse) {
		this.adressekunde = adresse;
	}
	
	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public Bankverbindung getBankverbindung() {
		return bankverbindung;
	}

	public void setBankverbindung(Bankverbindung bankverbindung) {
		this.bankverbindung = bankverbindung;
	}
	
	public Date getSeit() {
		return seit;
	}

	public void setSeit(Date seit) {
		this.seit = seit;
	}
	
	public List<Auftrag> getBestellungen() {
		return auftraege;
	}
	public void setBestellungen(List<Auftrag> auftraege) {
		this.auftraege = auftraege;
	}

	public URI getBestellungenUri() {
		return bestellungenUri;
	}
	public void setBestellungenUri(URI bestellungenUri) {
		this.bestellungenUri = bestellungenUri;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((adressekunde == null) ? 0 : adressekunde.hashCode());
		result = prime * result + ((anrede == null) ? 0 : anrede.hashCode());
		result = prime * result
				+ ((auftraege == null) ? 0 : auftraege.hashCode());
		result = prime * result
				+ ((bankverbindung == null) ? 0 : bankverbindung.hashCode());
		result = prime * result
				+ ((bestellungenUri == null) ? 0 : bestellungenUri.hashCode());
		result = prime * result + ((eMail == null) ? 0 : eMail.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((nachname == null) ? 0 : nachname.hashCode());
		result = prime * result + ((seit == null) ? 0 : seit.hashCode());
		result = prime * result + ((telefon == null) ? 0 : telefon.hashCode());
		result = prime * result + ((vorname == null) ? 0 : vorname.hashCode());
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
		Kunde other = (Kunde) obj;
		if (adressekunde == null) {
			if (other.adressekunde != null)
				return false;
		} else if (!adressekunde.equals(other.adressekunde))
			return false;
		if (anrede == null) {
			if (other.anrede != null)
				return false;
		} else if (!anrede.equals(other.anrede))
			return false;
		if (auftraege == null) {
			if (other.auftraege != null)
				return false;
		} else if (!auftraege.equals(other.auftraege))
			return false;
		if (bankverbindung == null) {
			if (other.bankverbindung != null)
				return false;
		} else if (!bankverbindung.equals(other.bankverbindung))
			return false;
		if (bestellungenUri == null) {
			if (other.bestellungenUri != null)
				return false;
		} else if (!bestellungenUri.equals(other.bestellungenUri))
			return false;
		if (eMail == null) {
			if (other.eMail != null)
				return false;
		} else if (!eMail.equals(other.eMail))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nachname == null) {
			if (other.nachname != null)
				return false;
		} else if (!nachname.equals(other.nachname))
			return false;
		if (seit == null) {
			if (other.seit != null)
				return false;
		} else if (!seit.equals(other.seit))
			return false;
		if (telefon == null) {
			if (other.telefon != null)
				return false;
		} else if (!telefon.equals(other.telefon))
			return false;
		if (vorname == null) {
			if (other.vorname != null)
				return false;
		} else if (!vorname.equals(other.vorname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Kunde [bestellungenUri=" + bestellungenUri + ", id=" + id
				+ ", anrede=" + anrede + ", eMail=" + eMail + ", nachname="
				+ nachname + ", vorname=" + vorname + ", telefon=" + telefon
				+ ", adressekunde=" + adressekunde + ", bankverbindung="
				+ bankverbindung + ", seit=" + seit + ", auftraege="
				+ auftraege + "]";
	}




}