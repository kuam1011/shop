package de.shop.bestellverwaltung.rest;

import static de.shop.util.Constants.FIRST_LINK;
import static de.shop.util.Constants.LAST_LINK;
import static de.shop.util.Constants.SELF_LINK;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_XML;

import java.net.URI;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import de.shop.bestellverwaltung.domain.Auftrag;
import de.shop.bestellverwaltung.domain.Lieferant;
import de.shop.bestellverwaltung.domain.Rechnung;
import de.shop.bestellverwaltung.service.AuftragService;
import de.shop.util.NotFoundException;
import de.shop.util.UriHelper;


@Path("/auftrag")
@Produces({ APPLICATION_JSON, APPLICATION_XML + ";qs=0.75", TEXT_XML + ";qs=0.5" })
@Consumes
@RequestScoped
public class AuftragResource {
	
	@Context
	private UriInfo uriInfo;
	@Inject
	private UriHelper uriHelper;
	@Inject
	private RechnungResource rechnungResource;
	@Inject
	private LieferantResource lieferantResource;
	@Inject
	private AuftragService as;
	
	@GET
	@Path("{id:[1-9][0-9]*}")
	public Response findAuftragById(@PathParam("id") Long id) {
		final Auftrag auftrag = as.findAuftragById(id);
		
		if (auftrag == null)
			throw new NotFoundException("auftrag.notFound.id");
		
		// Link-Header setzen
		final Response response = Response.ok(auftrag)
                                          .links(getTransitionalLinks(auftrag, uriInfo))
                                          .build();
		
		return response;
	}
	
	@GET
	public Response findAuftraege() {
		final List<Auftrag> auftraege = as.findAllAuftraege();
		
		return Response.ok(new GenericEntity<List<Auftrag>>(auftraege) { })
                       .links(getTransitionalLinksAuftraege(auftraege, uriInfo))
                       .build();
	}
	
	@GET
	@Path("{id:[1-9][0-9]*}/rechnung")
	public Response findRechnungenByAuftragId(@PathParam("id") Long auftragId) {
		final List<Rechnung> rechnungen = as.findRechnungenByAuftragId(auftragId);
		
		if (rechnungen.isEmpty()) {
			throw new NotFoundException("auftrag.notFound.rechnungen.id");
		}
		
		// URIs innerhalb der gefundenen Rechnungen anpassen
		for (Rechnung rechnung : rechnungen) {
			rechnungResource.setStructuralLinksRechnung(rechnung, uriInfo);
		}
		
		return Response.ok(new GenericEntity<List<Rechnung>>(rechnungen) { })
                       .links(getTransitionalLinksRechnungen(rechnungen, uriInfo))
                       .build();
	}

	@GET
	@Path("{id:[1-9][0-9]*}/lieferant")
	public Response findLieferantenByAuftragId(@PathParam("id") Long auftragId) {
		final List<Lieferant> lieferanten = as.findLieferantenByAuftragId(auftragId);
		if (lieferanten.isEmpty()) {
			throw new NotFoundException("auftrag.notFound.lieferanten.id");
		}
		
		// URIs innerhalb der gefundenen Lieferanten anpassen
		for (Lieferant lieferant : lieferanten) {
			lieferantResource.setStructuralLinksLieferant(lieferant, uriInfo);
		}
		
		return Response.ok(new GenericEntity<List<Lieferant>>(lieferanten) { })
                       .links(getTransitionalLinksLieferanten(lieferanten, uriInfo))
                       .build();
	}
	
	@POST
	@Consumes({ APPLICATION_JSON, APPLICATION_XML, TEXT_XML })
	@Produces
	@Path("{id:[1-9][0-9]*}/lieferant")
	public Response createLieferant(@PathParam("id") Long auftragId, Lieferant lieferant) {
		final Lieferant neuerLieferant = as.createLieferant(auftragId, lieferant);
		
		return Response.created(
				getUriLieferant(neuerLieferant, this.uriInfo))
				.build();
	}
	
	@POST
	@Consumes({ APPLICATION_JSON, APPLICATION_XML, TEXT_XML })
	@Produces
	@Path("{id:[1-9][0-9]*}/rechnung")
	public Response createRechnung(@PathParam("id") Long auftragId, Rechnung rechnung) {
		final Rechnung neueRechnung = as.createRechnung(auftragId, rechnung);
		
		return Response.created(
				getUriRechnung(neueRechnung, this.uriInfo))
				.build();
	}

	private Link[] getTransitionalLinksRechnungen(List<Rechnung> rechnungen, UriInfo uriInfo) {
		if (rechnungen == null || rechnungen.isEmpty()) {
			return null;
		}
		
		final Link first = Link.fromUri(getUriRechnung(rechnungen.get(0), uriInfo))
	                           .rel(FIRST_LINK)
	                           .build();
		final int lastPos = rechnungen.size() - 1;
		final Link last = Link.fromUri(getUriRechnung(rechnungen.get(lastPos), uriInfo))
                              .rel(LAST_LINK)
                              .build();
		
		return new Link[] {first, last};
	}

	private Link[] getTransitionalLinksLieferanten(List<Lieferant> lieferanten, UriInfo uriInfo) {
		if (lieferanten == null || lieferanten.isEmpty()) {
			return null;
		}
		
		final Link first = Link.fromUri(getUriLieferant(lieferanten.get(0), uriInfo))
	                           .rel(FIRST_LINK)
	                           .build();
		final int lastPos = lieferanten.size() - 1;
		final Link last = Link.fromUri(getUriLieferant(lieferanten.get(lastPos), uriInfo))
                              .rel(LAST_LINK)
                              .build();
		
		return new Link[] {first, last};
	}
	
	public URI getUriRechnung(Rechnung rechnung, UriInfo uriInfo) {
		return uriHelper.getUri(RechnungResource.class, "findRechnungById", rechnung.getId(), uriInfo);
	}

	public URI getUriLieferant(Lieferant lieferant, UriInfo uriInfo) {
		return uriHelper.getUri(LieferantResource.class, "findLieferantById", lieferant.getId(), uriInfo);
	}
	
	public void setStructuralLinks(Auftrag auftrag, UriInfo uriInfo) {
		// URI fuer Rechnungen setzen
		final URI uri = getUriRechnungen(auftrag, uriInfo);
		auftrag.setRechnungenUri(uri);
	}

	private URI getUriRechnungen(Auftrag kunde, UriInfo uriInfo) {
		return uriHelper.getUri(AuftragResource.class, "findRechnungenByAuftragId", kunde.getId(), uriInfo);
	}		
	
	private Link[] getTransitionalLinks(Auftrag auftrag, UriInfo uriInfo) {
		final Link self = Link.fromUri(getUriAuftrag(auftrag, uriInfo))
                              .rel(SELF_LINK)
                              .build();
		return new Link[] {self};
	}
	
	private Link[] getTransitionalLinksAuftraege(List<Auftrag> auftraege, UriInfo uriInfo) {
		if (auftraege == null || auftraege.isEmpty())
			return null;
		
		final Link first = Link.fromUri(getUriAuftrag(auftraege.get(0), uriInfo))
	                           .rel(FIRST_LINK)
	                           .build();
		final int lastPos = auftraege.size() - 1;
		final Link last = Link.fromUri(getUriAuftrag(auftraege.get(lastPos), uriInfo))
                              .rel(LAST_LINK)
                              .build();
		
		return new Link[] {first, last};
	}
	
	public URI getUriAuftrag(Auftrag auftrag, UriInfo uriInfo) {
		return uriHelper.getUri(AuftragResource.class, "findAuftragById", auftrag.getId(), uriInfo);
	}

	/**
	 * Mit der URL /bestellungen eine neue Bestellung anlegen
	 * @param bestellung die neue Bestellung
	 * @return Objekt mit Bestelldaten, falls die ID vorhanden ist
	 */
	@POST
	@Consumes({ APPLICATION_JSON, APPLICATION_XML, TEXT_XML })
	@Produces
	public Response createAuftrag(@Valid Auftrag auftrag) {
		final Auftrag neuerAuftrag = as.createAuftrag(auftrag);
		final URI auftragUri = getUriAuftrag(neuerAuftrag, uriInfo);
		return Response.created(auftragUri).build();
	}
	
	@PUT
	@Consumes({APPLICATION_JSON, APPLICATION_XML, TEXT_XML })
	@Produces
	public void updateAuftrag(@Valid Auftrag auftrag) {
		as.createAuftrag(auftrag);
	}
	
	@DELETE
	@Path("{id:[1-9][0-9]*}")
	@Produces
	public void deleteAuftrag(@PathParam("id") Long auftragId) {
		as.deleteAuftrag(auftragId);
	}
}
