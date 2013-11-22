package de.shop.kundenverwaltung.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Bankverbindung implements Serializable {

	private static final long serialVersionUID = 7480998754450482596L;
	
	//@Id
	//@GeneratedValue
	//@Column(name = "id", nullable = false, updatable = false)
	@XmlAttribute
	private Long id;
	
	//@Column(name = "kontonr", nullable = false)
	@XmlElement(required = true)
	private String knr;
	
	//@Column(name = "blz", nullable = false)
	@XmlElement(required = true)
	private String blz;
	
	//@Column(name = "bankname", nullable = false)
	@XmlElement(required = true)
	private String bankname;

	public Bankverbindung (Long id, String knr, String blz, String bankname)
	{
		super();
		setId(id);
		setKnr(knr);
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
