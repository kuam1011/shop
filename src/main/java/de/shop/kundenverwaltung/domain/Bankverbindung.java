package de.shop.kundenverwaltung.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Bankverbindung implements Serializable {


	private static final long serialVersionUID = 7480998754450482596L;
	
	private static final int KONTONUMMER_LENGTH_MIN = 5;
	private static final int KONTONUMMER_LENGTH_MAX = 10;
	private static final int BANKNAME_LENGTH_MIN = 2;
	private static final int BANKNAME_LENGTH_MAX = 30;
	
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false, updatable = false)
	@XmlAttribute
	private Long id;
	
	@NotNull(message = "{bankverbindung.kontonummer.notNull}")
	@Size(min = KONTONUMMER_LENGTH_MIN, max = KONTONUMMER_LENGTH_MAX, message = "{bankverbindung.kontonummer.length}")
	@Column(name = "kontonr", nullable = false)
	@XmlElement(required = true)
	private String knr;
	
	@NotNull(message = "{bankverbindung.blz.notNull}")
	@Pattern(regexp = "\\d{8}", message = "{bankverbindung.plz.pattern}")
	@Column(name = "blz", nullable = false)
	@XmlElement(required = true)
	private String blz;
	
	@NotNull(message = "{bankverbindung.bankname.notNull}")
	@Size(min = BANKNAME_LENGTH_MIN, max = BANKNAME_LENGTH_MAX, message = "{bankverbindung.kontonummer.length}")
	@Column(name = "bankname", nullable = false)
	@XmlElement(required = true)
	private String bankname;

	public Bankverbindung (Long id, String knr, String blz, String bankname)
	{
		super();
		setKnr(knr);
		setId(id);
		setBlz(blz);
		setBankname(bankname);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKnr() {
		return knr;
	}

	public void setKnr(String knr) {
		this.knr = knr;
	}

	public String getBlz() {
		return blz;
	}

	public void setBlz(String blz) {
		this.blz = blz;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bankname == null) ? 0 : bankname.hashCode());
		result = prime * result + ((blz == null) ? 0 : blz.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((knr == null) ? 0 : knr.hashCode());
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
		Bankverbindung other = (Bankverbindung) obj;
		if (bankname == null) {
			if (other.bankname != null)
				return false;
		} else if (!bankname.equals(other.bankname))
			return false;
		if (blz == null) {
			if (other.blz != null)
				return false;
		} else if (!blz.equals(other.blz))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (knr == null) {
			if (other.knr != null)
				return false;
		} else if (!knr.equals(other.knr))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bankverbindung [id=" + id + ", knr=" + knr + ", blz=" + blz
				+ ", bankname=" + bankname + "]";
	}
	
	
	
	
	

}
