package de.shop.artikelverwaltung.domain;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_XML;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@XmlRootElement
@XmlSeeAlso({ Fahrrad.class })
@Path("produkt")
@Produces({ APPLICATION_JSON, APPLICATION_XML + ";qs=0.75", TEXT_XML + ";qs=0.75" })
public class Produkt {
	
	enum Verfügbarkeit {
		istVerfügbar, istNichtVerfügbar, istNachbestellt, istNichtMehrImSortiment,
	}
	
	@Id
	@GeneratedValue
	@XmlAttribute
	private Long id;
	
	@NotNull
	@NotEmpty
	@Pattern(regexp = "\\d{8}")
	private String produktnummer;

	@NotNull
	@Size(min = 4, max = 50)												
	private String bezeichnung;
	
	@Size(min = 4, max = 15)
	@NotNull
	private String hersteller;

	@NotNull
	@DecimalMin("0.99")
	@Digits(integer = 6, fraction = 2)
	private BigDecimal preis;
	
	private Verfügbarkeit status;
	
	@DecimalMin("0.00")
	@DecimalMax("0.80")
	private BigDecimal rabatt;												
	
	public BigDecimal getPreis() {
//		if (rabatt.equals(BigDecimal.ZERO) || rabatt.equals(null)) {
//			return preis;
//		}
//		preis = preis.multiply(rabatt);
		return preis;
	}

	public void setPreis(BigDecimal preis) {
		this.preis = preis;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public String getProduktnummer() {
		return produktnummer;
	}

	public void setProduktnummer(String produktnummer) {
		this.produktnummer = produktnummer;
	}

	public Verfügbarkeit getStatus() {
		return status;
	}

	public void setStatus(Verfügbarkeit status) {
		this.status = status;
	}

	public String getHersteller() {
		return hersteller;
	}

	public void setHersteller(String hersteller) {
		this.hersteller = hersteller;
	}

	public Long getid() {
		return this.id;
	}

	public void setid(Long id) {
		this.id = id;
	}

	public BigDecimal getRabatt() {
		return rabatt;
	}

	public void setRabatt(BigDecimal rabatt) {
		this.rabatt = rabatt;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((bezeichnung == null) ? 0 : bezeichnung.hashCode());
		result = prime * result
				+ ((hersteller == null) ? 0 : hersteller.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((preis == null) ? 0 : preis.hashCode());
		result = prime * result
				+ ((produktnummer == null) ? 0 : produktnummer.hashCode());
		result = prime * result + ((rabatt == null) ? 0 : rabatt.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		final Produkt other = (Produkt) obj;
		if (bezeichnung == null) {
			if (other.bezeichnung != null)
				return false;
		} 
		else if (!bezeichnung.equals(other.bezeichnung))
			return false;
		if (hersteller == null) {
			if (other.hersteller != null)
				return false;
		} 
		else if (!hersteller.equals(other.hersteller))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} 
		else if (!id.equals(other.id))
			return false;
		if (preis == null) {
			if (other.preis != null)
				return false;
		} 
		else if (!preis.equals(other.preis))
			return false;
		if (produktnummer == null) {
			if (other.produktnummer != null)
				return false;
		} 
		else if (!produktnummer.equals(other.produktnummer))
			return false;
		if (rabatt == null) {
			if (other.rabatt != null)
				return false;
		} 
		else if (!rabatt.equals(other.rabatt))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Produkt [id=" + id + ", produktnummer=" + produktnummer
				+ ", bezeichnung=" + bezeichnung + ", hersteller=" + hersteller
				+ ", preis=" + preis + ", status=" + status + ", rabatt="
				+ rabatt + "]";
	}

}
