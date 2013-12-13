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
import de.shop.bestellverwaltung.service.LieferantService;
import de.shop.util.NotFoundException;
import de.shop.util.UriHelper;


@Path("/lieferant")
@Produces({ APPLICATION_JSON, APPLICATION_XML + ";qs=0.75", TEXT_XML + ";qs=0.5" })
@Consumes
@RequestScoped
public class LieferantResource {
	@Context
	private UriInfo uriInfo;
	@Inject
	private UriHelper uriHelper;
	@Inject
	private AuftragResource auftragResource;
	@Inject
	private LieferantService ls;
	
	@GET
	public Response findLieferanten() {
		final List<Lieferant> rechnungen = ls.findAllLieferanten();

		return Response.ok(new GenericEntity<List<Lieferant>>(rechnungen) {
		}).links(getTransitionalLinksLieferanten(rechnungen, this.uriInfo))
				.build();
	}

	@GET
	@Path("{id:[1-9][0-9]*}")
	public Response findLieferantById(@PathParam("id") Long id) {
		final Lieferant rechnung = ls.findLieferantById(id);
		if (rechnung == null)
			throw new NotFoundException("Kein Lieferant mit der ID " + id + " gefunden.");

		// Link-Header setzen
		final Response response = Response.ok(rechnung)
				.links(getTransitionalLinks(rechnung, this.uriInfo)).build();

		return response;
	}

	private Link[] getTransitionalLinks(Lieferant rechnung, UriInfo uriInfo) {
		final Link self = Link.fromUri(getUriLieferant(rechnung, uriInfo))
				.rel(SELF_LINK).build();
		return new Link[] {self};
	}

	private Link[] getTransitionalLinksLieferanten(List<Lieferant> lieferanten,
			UriInfo uriInfo) {
		if (lieferanten == null || lieferanten.isEmpty())
			return null;

		final Link first = Link
				.fromUri(getUriLieferant(lieferanten.get(0), uriInfo))
				.rel(FIRST_LINK).build();
		final int lastPos = lieferanten.size() - 1;
		final Link last = Link
				.fromUri(getUriLieferant(lieferanten.get(lastPos), uriInfo))
				.rel(LAST_LINK).build();

		return new Link[] {first, last};
	}

	public URI getUriLieferant(Lieferant lieferant, UriInfo uriInfo) {
		return uriHelper.getUri(LieferantResource.class, "findLieferantById",
				lieferant.getId(), uriInfo);
	}

	@POST
	@Consumes({ APPLICATION_JSON, APPLICATION_XML, TEXT_XML })
	@Produces
	public Response createLieferant(Lieferant lieferant) {
		final Lieferant neuerLieferant = ls.createLieferant(lieferant);
		return Response.created(
				getUriLieferant(neuerLieferant, this.uriInfo))
				.build();
	}

	@PUT
	@Consumes({ APPLICATION_JSON, APPLICATION_XML, TEXT_XML })
	@Produces
	public void updateLieferant(Lieferant lieferant) {
		ls.updateLieferant(lieferant);
	}

	@DELETE
	@Path("{id:[1-9][0-9]*}")
	@Produces
	public void deleteLieferant(@PathParam("id") Long liefernantId) {
		ls.deleteLieferant(liefernantId);
	}

	public void setStructuralLinksLieferant(Lieferant lieferant, UriInfo uriInfo) {
		// URI fuer Kunde setzen
		final Auftrag auftrag = lieferant.getAuftrag();
		if (auftrag != null) {
			final URI auftragUri = auftragResource.getUriAuftrag(
					lieferant.getAuftrag(), uriInfo);
			lieferant.setAuftragUri(auftragUri);
		}
	}

	public void setStructuralLinksRechnung(Rechnung rechnung, UriInfo uriInfo) {
		// URI fuer Kunde setzen
		final Auftrag auftrag = rechnung.getAuftrag();
		if (auftrag != null) {
			final URI auftragUri = auftragResource.getUriAuftrag(
					rechnung.getAuftrag(), uriInfo);
			rechnung.setAuftragUri(auftragUri);
		}
	}
}
