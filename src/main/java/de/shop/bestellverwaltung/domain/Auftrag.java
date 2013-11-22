package de.shop.bestellverwaltung.domain;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_XML;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;



@XmlRootElement
@Path("auftrag")
@Produces({APPLICATION_JSON, APPLICATION_XML + ";qs=0.75", TEXT_XML + ";qs=0.75"})
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
	
	private Long id;
	private AuftragsStatus status;
	private URI rechnungenUri;
	@XmlTransient
	final private List<Lieferant> lieferant;
	@XmlTransient
	final private List<Rechnung> rechnung;
	
	/**
	 * @param nr Auftragsnummer.
	 */
	public Auftrag() {
		super();
		this.status = AuftragsStatus.InBearbeitung;
		this.lieferant = new ArrayList<Lieferant>();
		this.rechnung = new ArrayList<Rechnung>();
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
		if (this.lieferant.contains(lieferant))
			throw new LieferantException("Lieferant bereits vorhanden.");
		
		this.lieferant.add(lieferant);
	}
	
	/**
	 * @param lieferant Lieferant.
	 * @throws LieferantException
	 */
	public void delLieferant(Lieferant lieferant) throws LieferantException {
		if (!this.lieferant.contains(lieferant))
			throw new LieferantException("Gesuchter Lieferant nicht vorhanden.");
		
		this.lieferant.remove(lieferant);
	}
	
	/**
	 * @param id Lieferantennummer.
	 * @return Lieferant.
	 * @throws LieferantException
	 */
	
	//TODO Referenzen werden verglichen, sollen aber Werte verglichen werden
	public Lieferant getLieferant(Long id) throws LieferantException {		
		for (Lieferant lieferant : this.lieferant) {
			if (lieferant.getId().equals(id))
				return lieferant;
		}
		
		throw new LieferantException("Gesuchter Lieferant nicht vorhanden.");
	}
	
	/**
	 * @return Lieferantenliste.
	 */
	public List<Lieferant> getLieferantAll() {
		return this.lieferant;
	}
	
	/**
	 * @param rechnung Rechnung.
	 * @throws RechnungException
	 */
	public void addRechnung(Rechnung rechnung) throws RechnungException {
		if (this.rechnung.contains(rechnung))
			throw new RechnungException("Rechnung bereits vorhanden.");
		
		this.rechnung.add(rechnung);
	}
	
	/**
	 * @param rechnung Rechnung.
	 * @throws RechnungException
	 */
	public void delRechnung(Rechnung rechnung) throws RechnungException {
		if (!this.rechnung.contains(rechnung))
			throw new RechnungException("Gesuchte Rechnung nicht vorhanden.");
		
		this.rechnung.remove(rechnung);
	}
	
	/**
	 * @param id Rechnungsnummer.
	 * @return Rechnung.
	 * @throws RechnungException
	 */
	public Rechnung getRechnung(Long id) throws RechnungException {
		if (this.rechnung == null)
			throw new RechnungException("Gesuchte Rechnung nicht vorhanden.");
		
		
		//TODO Referenzen werden verglichen, sollen aber Werte verglichen werden
		for (Rechnung rechnung : this.rechnung) {
			if (rechnung.getId().equals(id))
				return rechnung;
		}
		
		throw new RechnungException("Gesuchte Rechnung nicht vorhanden.");
	}
	
	/**
	 * @return Rechnungsliste.
	 */
	public List<Rechnung> getRechnungAll() {
		return this.rechnung;
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
				+ ((lieferant == null) ? 0 : lieferant.hashCode());
		result = prime * result
				+ ((rechnung == null) ? 0 : rechnung.hashCode());
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
		} else if (!id.equals(other.id))
			return false;
		if (lieferant == null) {
			if (other.lieferant != null)
				return false;
		} else if (!lieferant.equals(other.lieferant))
			return false;
		if (rechnung == null) {
			if (other.rechnung != null)
				return false;
		} else if (!rechnung.equals(other.rechnung))
			return false;
		if (status != other.status)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Auftrag [getId()=" + getId() + ", getStatus()=" + getStatus()
				+ ", getLieferantAll()=" + getLieferantAll()
				+ ", getRechnungAll()=" + getRechnungAll() + "]";
	}

	/*
	@POST
	public Response createAuftrag(Auftrag auftrag) {
		//einen neuen Auftrag anlegen
		URI myuri = URI.create("http://.../auftrag/" + getId());
		return Response.created(myuri)
					   .build();
	}
	*/
}
