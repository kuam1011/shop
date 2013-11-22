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

@Entity
@XmlRootElement
@XmlSeeAlso({Fahrrad.class})
@Path("produkt")
@Produces({APPLICATION_JSON, APPLICATION_XML + ";qs=0.75", TEXT_XML + ";qs=0.75"})
public class Produkt {

	enum Verfügbarkeit {
		istVerfügbar, istNichtVerfügbar, istNachbestellt, istNichtMehrImSortiment,
	}
	
	@Id
	@GeneratedValue
	@NotNull
	@XmlAttribute
	private Long id;
	
	@NotNull
	@Pattern(regexp = "\\d{8}")
	private String produktnummer;

	@NotNull																			// sinnvoll?
	@Size(min = 4, max = 30)
	@Pattern(regexp = "\\w")																// \w?
	private String bezeichnung;

	@NotNull																//	größer 0!
	@DecimalMin("0.99")
	@Digits(integer = 6, fraction = 2)
	private BigDecimal preis;
	
	private Verfügbarkeit status;
	
	@DecimalMin("0.00")
	@DecimalMax("80.00")
	private BigDecimal rabatt;												//nicht negativ
	
	@NotNull
	@DecimalMin("0.99")
	private BigDecimal endpreis;

	public BigDecimal getPreis() {
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

	public BigDecimal getEndpreis() {
		return endpreis;
	}

	public void setEndpreis(BigDecimal endpreis) {
		this.endpreis = endpreis;
	}
	
	public BigDecimal endPreisBerechnen(BigDecimal Preis, BigDecimal Rabatt) {
		
		final BigDecimal oneHundred = new BigDecimal(100);
		final BigDecimal zero = new BigDecimal(0);
		BigDecimal endpreis = preis;
		if (Rabatt == zero || Rabatt == null)				//|| Rabatt < zero BEAN hat schon geprüft auch mit Null maber 
			return Preis;
		endpreis = (preis.divide(oneHundred)).multiply(oneHundred.subtract(Rabatt));
		return endpreis;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result
				+ ((bezeichnung == null) ? 0 : bezeichnung.hashCode());
		result = prime * result
				+ ((endpreis == null) ? 0 : endpreis.hashCode());
		result = prime * result + ((preis == null) ? 0 : preis.hashCode());
		result = prime * result
				+ ((produktnummer == null) ? 0 : produktnummer.hashCode());
		result = prime * result + ((rabatt == null) ? 0 : rabatt.hashCode());
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
		Produkt other = (Produkt) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (status != other.status)
			return false;
		if (bezeichnung == null) {
			if (other.bezeichnung != null)
				return false;
		}
		else if (!bezeichnung.equals(other.bezeichnung))
			return false;
		if (endpreis == null) {
			if (other.endpreis != null)
				return false;
		}
		else if (!endpreis.equals(other.endpreis))
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
		return true;
	}

	@Override
	public String toString() {
		return "Produkt [id=" + id + ", produktnummer=" + produktnummer
				+ ", bezeichnung=" + bezeichnung + ", preis=" + preis
				+ ", Status=" + status + ", rabatt=" + rabatt + ", endpreis="
				+ endpreis + "]";
	}
}
