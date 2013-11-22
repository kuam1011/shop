package de.shop.artikelverwaltung.rest;

import static de.shop.util.Constants.FIRST_LINK;
import static de.shop.util.Constants.LAST_LINK;
import static de.shop.util.Constants.SELF_LINK;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_XML;

import java.net.URI;
import java.util.List;

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

import de.shop.artikelverwaltung.domain.Produkt;
import de.shop.util.Mock;
import de.shop.util.NotFoundException;
import de.shop.util.UriHelper;

@Path("/produkt")
@Produces({APPLICATION_JSON, APPLICATION_XML + ";qs=0.75", TEXT_XML + ";qs=0.5"})
@Consumes
public class ProduktResource {
	
	public static final String PRODUKT_ID_PATH_PARAM = "id";
	//private static final String NOT_FOUND_ID = "Produkt.notFound.id";
	
	@Context
	private UriInfo uriInfo;
	
	@Inject
	private UriHelper uriHelper;
	
	@GET
	public Response findProdukte() {
		List<Produkt> produkte = Mock.findAllProdukte();

		return Response.ok(new GenericEntity<List<Produkt>>(produkte) {
		}).links(getTransitionalLinksProdukte(produkte, this.uriInfo))
				.build();
	}

	@GET
	@Path("{id:[1-9][0-9]*}")
	public Response findProduktById(
			@PathParam(PRODUKT_ID_PATH_PARAM) Long id) {
		final Produkt produkt = Mock.findProduktById(id);
		if (produkt == null)
			throw new NotFoundException("Kein Produkt mit der ID " + id
					+ " gefunden.");

		final Response response = Response.ok(produkt)
				.links(getTransitionalLinks(produkt, this.uriInfo)).build();

		return response;
	}

	private Link[] getTransitionalLinks(Produkt produkt, UriInfo uriInfo) {
		final Link self = Link.fromUri(getUriProdukt(produkt, uriInfo))
				.rel(SELF_LINK).build();
		return new Link[] {self};
	}

	private Link[] getTransitionalLinksProdukte(List<Produkt> produkte,
			UriInfo uriInfo) {
		if (produkte == null || produkte.isEmpty())
			return null;

		final Link first = Link
				.fromUri(getUriProdukt(produkte.get(0), uriInfo))
				.rel(FIRST_LINK).build();
		final int lastPos = produkte.size() - 1;
		final Link last = Link
				.fromUri(getUriProdukt(produkte.get(lastPos), uriInfo))
				.rel(LAST_LINK).build();

		return new Link[] {first, last};
	}

	public URI getUriProdukt(Produkt produkt, UriInfo uriInfo) {
		return uriHelper.getUri(ProduktResource.class, "findProduktById",
				produkt.getid(), uriInfo);
	}
					
	@POST
	@Consumes({ APPLICATION_JSON, APPLICATION_XML, TEXT_XML })
	@Produces
	public Response createProdukt(@Valid Produkt produkt) {
		System.out.println("Neues Produkt mit id = " + produkt.getid() + " angelegt.");
		final URI produktUri = getUriProdukt(produkt, uriInfo);
		return Response.created(produktUri).build();
	}
		
	@PUT
	@Consumes({APPLICATION_JSON, APPLICATION_XML, TEXT_XML })
	@Produces
	public void updateProdukt(Produkt produkt) {
		Mock.updateProdukt(produkt);
	}
	
	@DELETE
	@Path("{id:[1-9][0-9]*}")
	@Produces
	public void deleteProdukt(@PathParam("id") Long produktId) {
		Mock.deleteProdukt(produktId);
	}
}
