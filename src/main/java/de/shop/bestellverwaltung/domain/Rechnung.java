package de.shop.bestellverwaltung.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URI;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Rechnung implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1100850404374054249L;

	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;
	
	private Boolean istBezahlt;
	
	@NotNull(message = "{rechnung.summe.notNull}")
	@DecimalMin("0.0")
	private BigDecimal summe;
	
	private URI auftragUri;
	
	@NotNull(message = "{rechnung.auftrag.notNull}")
	@Valid
	private Auftrag auftrag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		if (id == null)
			throw new NullPointerException("Rechnungsnummer darf nicht leer sein.");

		this.id = id;
	}
	
	public Auftrag getAuftrag() {
		return auftrag;
	}

	public void setAuftrag(Auftrag auftrag) {
		this.auftrag = auftrag;
	}

	public URI getAuftragUri() {
		return auftragUri;
	}
	
	public void setAuftragUri(URI auftragUri) {
		this.auftragUri = auftragUri;
	}
	
	/**
	 * @return Bezahltstatus.
	 */
	public Boolean getIstBezahlt() {
		return istBezahlt;
	}

	/**
	 * @param istBezahlt Bezahltstatus.
	 */
	public void setIstBezahlt(Boolean istBezahlt) {
		if (istBezahlt == null)
			throw new NullPointerException("Bezahlungsstatus muss richtig oder falsch sein.");
		
		this.istBezahlt = istBezahlt;
	}

	/**
	 * @return Monetäre Summe.
	 */
	public BigDecimal getSumme() {
		return summe;
	}

	/**
	 * @param summe Monetäre Summe.
	 */
	public void setSumme(BigDecimal summe) {
		if (summe == null || summe.compareTo(BigDecimal.ZERO) < 0)
			throw new NullPointerException("Summe muss einen positiven oder neutralen Wert haben.");
		
		this.summe = summe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((istBezahlt == null) ? 0 : istBezahlt.hashCode());
		result = prime * result + ((summe == null) ? 0 : summe.hashCode());
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
		final Rechnung other = (Rechnung) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (istBezahlt == null) {
			if (other.istBezahlt != null)
				return false;
		}
		else if (!istBezahlt.equals(other.istBezahlt))
			return false;
		if (summe == null) {
			if (other.summe != null)
				return false;
		}
		else if (!summe.equals(other.summe))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Rechnung [getId()=" + getId() + ", getIstBezahlt()="
				+ getIstBezahlt() + ", getSumme()=" + getSumme() + "]";
	}
}
