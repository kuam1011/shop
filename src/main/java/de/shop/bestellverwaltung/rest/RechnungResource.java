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
import de.shop.bestellverwaltung.service.RechnungService;
import de.shop.util.NotFoundException;
import de.shop.util.UriHelper;


@Path("/rechnung")
@Produces({ APPLICATION_JSON, APPLICATION_XML + ";qs=0.75", TEXT_XML + ";qs=0.5" })
@Consumes
@RequestScoped
public class RechnungResource {
	@Context
	private UriInfo uriInfo;
	@Inject
	private UriHelper uriHelper;
	@Inject
	private AuftragResource auftragResource;
	@Inject
	private RechnungService rs;
	
	@GET
	public Response findRechnungen() {
		final List<Rechnung> rechnungen = rs.findAllRechnungen();
		return Response.ok(new GenericEntity<List<Rechnung>>(rechnungen) {
		}).links(getTransitionalLinksRechnungen(rechnungen, this.uriInfo))
				.build();
	}

	@GET
	@Path("{id:[1-9][0-9]*}")
	public Response findRechnungById(@PathParam("id") Long id) {
		final Rechnung rechnung = rs.findRechnungById(id);
		if (rechnung == null)
			throw new NotFoundException("Kein Rechnung mit der ID " + id + " gefunden.");

		// Link-Header setzen
		final Response response = Response.ok(rechnung)
				.links(getTransitionalLinks(rechnung, this.uriInfo)).build();

		return response;
	}

	private Link[] getTransitionalLinks(Rechnung rechnung, UriInfo uriInfo) {
		final Link self = Link.fromUri(getUriRechnung(rechnung, uriInfo))
				.rel(SELF_LINK).build();
		return new Link[] {self};
	}

	private Link[] getTransitionalLinksRechnungen(List<Rechnung> rechnungen,
			UriInfo uriInfo) {
		if (rechnungen == null || rechnungen.isEmpty())
			return null;

		final Link first = Link
				.fromUri(getUriRechnung(rechnungen.get(0), uriInfo))
				.rel(FIRST_LINK).build();
		final int lastPos = rechnungen.size() - 1;
		final Link last = Link
				.fromUri(getUriRechnung(rechnungen.get(lastPos), uriInfo))
				.rel(LAST_LINK).build();

		return new Link[] {first, last};
	}

	public URI getUriRechnung(Rechnung rechnung, UriInfo uriInfo) {
		return uriHelper.getUri(RechnungResource.class, "findRechnungById",
				rechnung.getId(), uriInfo);
	}

	@POST
	@Consumes({ APPLICATION_JSON, APPLICATION_XML, TEXT_XML })
	@Produces
	public Response createRechnung(Rechnung rechnung) {
		final Rechnung neueRechnung = rs.createRechnung(rechnung);
		return Response.created(
				getUriRechnung(neueRechnung, this.uriInfo))
				.build();
	}

	@PUT
	@Consumes({ APPLICATION_JSON, APPLICATION_XML, TEXT_XML })
	@Produces
	public void updateRechnung(Rechnung rechnung) {
		rs.updateRechnung(rechnung);
	}

	@DELETE
	@Path("{id:[1-9][0-9]*}")
	@Produces
	public void deleteRechnung(@PathParam("id") Long liefernantId) {
		rs.deleteRechnung(liefernantId);
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

	public void setStructuralLinksLieferant(Lieferant lieferant, UriInfo uriInfo) {
		// URI fuer Kunde setzen
		final Auftrag auftrag = lieferant.getAuftrag();
		if (auftrag != null) {
			final URI auftragUri = auftragResource.getUriAuftrag(
					lieferant.getAuftrag(), uriInfo);
			lieferant.setAuftragUri(auftragUri);
		}
	}
}
