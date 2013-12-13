package de.shop.bestellverwaltung.domain;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_XML;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;



@XmlRootElement
@Path("auftrag")
@Produces({APPLICATION_JSON, APPLICATION_XML + ";qs=0.75", TEXT_XML + ";qs=0.75" })
@Consumes
public class Auftrag implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4021528490006520291L;

	public enum AuftragsStatus {
		InBearbeitung,
		Abgeschlossen
	}
	
	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;
	
	@NotNull(message = "{auftrag.status.notNull}")
	private AuftragsStatus status;
	
	private URI rechnungenUri;
	
	@XmlTransient
	@NotNull(message = "{auftrag.lieferanten.notNull}")
	@Valid
	@Size(min = 1)
	private final List<Lieferant> lieferanten;
	
	@XmlTransient
	@NotNull(message = "{auftrag.rechnungen.notNull}")
	@Valid
	@Size(min = 1)
	private final List<Rechnung> rechnungen;
	
	/**
	 * @param nr Auftragsnummer.
	 */
	public Auftrag() {
		super();
		this.status = AuftragsStatus.InBearbeitung;
		this.lieferanten = new ArrayList<Lieferant>();
		this.rechnungen = new ArrayList<Rechnung>();
	}
	
	/**
	 * @return Auftragsnummer.
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @param id Auftragsnummer.
	 */
	public void setId(Long id) {
		if (id == null)
			throw new NullPointerException("Id muss einen Wert haben.");

		this.id = id;
	}
		
	/**
	 * @return Auftragsstatus.
	 */
	public AuftragsStatus getStatus() {
		return status;
	}
	
	/**
	 * @param status Auftragsstatus.
	 */
	public void setStatus(AuftragsStatus status) {
		if (status == null)
			throw new NullPointerException("Auftragsstatus muss einen Wert haben.");
		
		this.status = status;
	}
	
	/**
	 * @param lieferant Lieferant.
	 * @throws LieferantException
	 */
	public void addLieferant(Lieferant lieferant) throws LieferantException {
		if (this.lieferanten.contains(lieferant))
			throw new LieferantException("Lieferant bereits vorhanden.");
		
		this.lieferanten.add(lieferant);
	}
	
	/**
	 * @param lieferant Lieferant.
	 * @throws LieferantException
	 */
	public void delLieferant(Lieferant lieferant) throws LieferantException {
		if (!this.lieferanten.contains(lieferant))
			throw new LieferantException("Gesuchter Lieferant nicht vorhanden.");
		
		this.lieferanten.remove(lieferant);
	}
	
	/**
	 * @param id Lieferantennummer.
	 * @return Lieferant.
	 * @throws LieferantException
	 */
	public Lieferant getLieferant(Long id) throws LieferantException {		
		for (Lieferant lieferant : this.lieferanten) {
			if (lieferant.getId().equals(id))
				return lieferant;
		}
		
		throw new LieferantException("Gesuchter Lieferant nicht vorhanden.");
	}
	
	/**
	 * @return Lieferantenliste.
	 */
	public List<Lieferant> getLieferantAll() {
		return this.lieferanten;
	}
	
	/**
	 * @param rechnung Rechnung.
	 * @throws RechnungException
	 */
	public void addRechnung(Rechnung rechnung) throws RechnungException {
		if (this.rechnungen.contains(rechnung))
			throw new RechnungException("Rechnung bereits vorhanden.");
		
		this.rechnungen.add(rechnung);
	}
	
	/**
	 * @param rechnung Rechnung.
	 * @throws RechnungException
	 */
	public void delRechnung(Rechnung rechnung) throws RechnungException {
		if (!this.rechnungen.contains(rechnung))
			throw new RechnungException("Gesuchte Rechnung nicht vorhanden.");
		
		this.rechnungen.remove(rechnung);
	}
	
	/**
	 * @param id Rechnungsnummer.
	 * @return Rechnung.
	 * @throws RechnungException
	 */
	public Rechnung getRechnung(Long id) throws RechnungException {
		if (this.rechnungen == null)
			throw new RechnungException("Gesuchte Rechnung nicht vorhanden.");
		
		for (Rechnung rechnung : this.rechnungen) {
			if (rechnung.getId().equals(id))
				return rechnung;
		}
		
		throw new RechnungException("Gesuchte Rechnung nicht vorhanden.");
	}
	
	/**
	 * @return Rechnungsliste.
	 */
	public List<Rechnung> getRechnungAll() {
		return this.rechnungen;
	}
	
	public URI getRechnungenUri() {
		return rechnungenUri;
	}

	public void setRechnungenUri(URI rechnungenUri) {
		this.rechnungenUri = rechnungenUri;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lieferanten == null) ? 0 : lieferanten.hashCode());
		result = prime * result
				+ ((rechnungen == null) ? 0 : rechnungen.hashCode());
		result = prime * result
				+ ((rechnungenUri == null) ? 0 : rechnungenUri.hashCode());
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
		final Auftrag other = (Auftrag) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (lieferanten == null) {
			if (other.lieferanten != null)
				return false;
		}
		else if (!lieferanten.equals(other.lieferanten))
			return false;
		if (rechnungen == null) {
			if (other.rechnungen != null)
				return false;
		}
		else if (!rechnungen.equals(other.rechnungen))
			return false;
		if (rechnungenUri == null) {
			if (other.rechnungenUri != null)
				return false;
		}
		else if (!rechnungenUri.equals(other.rechnungenUri))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Auftrag [id=" + id + ", status=" + status + ", rechnungenUri="
				+ rechnungenUri + ", lieferanten=" + lieferanten
				+ ", rechnungen=" + rechnungen + "]";
	}
}
