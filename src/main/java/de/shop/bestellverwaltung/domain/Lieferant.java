package de.shop.bestellverwaltung.domain;

import java.io.Serializable;
import java.net.URI;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

import de.shop.util.Adresse;


@XmlRootElement
public class Lieferant implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5999639397965054734L;

	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;
	
	@NotNull(message = "{lieferant.name.notNull}")
	@Pattern(regexp = "[A-ZÄÖÜ][a-zäöü]+", message = "{lieferant.name.pattern}")
	private String name;
	
	@NotNull(message = "{lieferant.lieferzeit.notNull}")
	@Future
	private Integer lieferzeit;
	
	private URI auftragUri;
	
	@NotNull(message = "{lieferant.auftrag.notNull}")
	@Valid
	private Auftrag auftrag;
	
	@NotNull(message = "{lieferant.adresse.notNull")
	@Valid
	private Adresse adresse;

	/**
	 * @return Lieferzeit.
	 */
	public Integer getLieferzeit() {
		return lieferzeit;
	}

	/**
	 * @param lieferzeit Lieferzeit in Tagen.
	 */
	public void setLieferzeit(Integer lieferzeit) {
		if (lieferzeit == null)
			throw new NullPointerException("Lieferant muss Lieferzeit haben.");
		if (lieferzeit <= 0)
			throw new NullPointerException("Lieferant muss Lieferzeit >0 Tage haben.");
		
		this.lieferzeit = lieferzeit;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	/**
	 * @return Lieferantennummer.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id Lieferantennummer.
	 */
	public void setId(Long id) {
		if (id == null)
			throw new NullPointerException("Lieferantennummer darf nicht leer sein.");

		this.id = id;
	}
	
	/**
	 * @return Lieferantenname.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name Lieferantenname.
	 */
	public void setName(String name) {
		if (name == null || "".equals(name))
			throw new NullPointerException("Lieferantenname darf nicht leer sein.");
		
		this.name = name;
	}

	public URI getAuftragUri() {
		return auftragUri;
	}

	public void setAuftragUri(URI auftragUri) {
		this.auftragUri = auftragUri;
	}

	public Auftrag getAuftrag() {
		return auftrag;
	}

	public void setAuftrag(Auftrag auftrag) {
		this.auftrag = auftrag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((auftrag == null) ? 0 : auftrag.hashCode());
		result = prime * result
				+ ((auftragUri == null) ? 0 : auftragUri.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lieferzeit == null) ? 0 : lieferzeit.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		final Lieferant other = (Lieferant) obj;
		if (auftrag == null) {
			if (other.auftrag != null)
				return false;
		}
		else if (!auftrag.equals(other.auftrag))
			return false;
		if (auftragUri == null) {
			if (other.auftragUri != null)
				return false;
		}
		else if (!auftragUri.equals(other.auftragUri))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (lieferzeit == null) {
			if (other.lieferzeit != null)
				return false;
		}
		else if (!lieferzeit.equals(other.lieferzeit))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lieferant [id=" + id + ", name=" + name + ", lieferzeit="
				+ lieferzeit + ", auftragUri=" + auftragUri + ", auftrag="
				+ auftrag + "]";
	}
}
