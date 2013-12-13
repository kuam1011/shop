package de.shop.kundenverwaltung.rest;

import static de.shop.util.Constants.ADD_LINK;
import static de.shop.util.Constants.FIRST_LINK;
import static de.shop.util.Constants.LAST_LINK;
import static de.shop.util.Constants.LIST_LINK;
import static de.shop.util.Constants.REMOVE_LINK;
import static de.shop.util.Constants.SELF_LINK;
import static de.shop.util.Constants.UPDATE_LINK;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import static javax.ws.rs.core.MediaType.TEXT_XML;

import java.net.URI;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.hibernate.validator.constraints.Email;

import de.shop.kundenverwaltung.service.KundeService;
import de.shop.kundenverwaltung.domain.Kunde;

import de.shop.util.Mock;
import de.shop.util.NotFoundException;
import de.shop.util.UriHelper;
import de.shop.util.interceptor.Log;


@Path("/kunden")
@Produces({ APPLICATION_JSON, APPLICATION_XML + ";qs=0.75", TEXT_XML + ";qs=0.5" })
@Consumes
@RequestScoped
@Log
public class KundeResource {
	public static final String KUNDEN_ID_PATH_PARAM = "id";
	public static final String KUNDEN_NACHNAME_QUERY_PARAM = "nachname";
	public static final String KUNDEN_EMAIL_QUERY_PARAM = "email";
	public static final String KUNDEN_PLZ_QUERY_PARAM = "plz";
	
	private static final String NAME_PATTERN = "[A-Z\u00C4\u00D6\u00DC][a-z\u00E4\u00F6\u00FC\u00DF]+";
	
	@Context
	private UriInfo uriInfo;
	
	@Inject
	private UriHelper uriHelper;
	
	@Inject
	private KundeService ks;
	
	@GET
	@Produces({ TEXT_PLAIN, APPLICATION_JSON })
	@Path("version")
	public String getVersion() {
		return "1.0";
	}
	
	// TODO Mock ersetzen
	@GET
	@Path("{" + KUNDEN_ID_PATH_PARAM + ":[1-9][0-9]*}")
	public Response findKundeById(@PathParam(KUNDEN_ID_PATH_PARAM) Long id) {
		final Kunde kunde = ks.findKundeById(id);
		
		// Fehlersuche @Path
		//setStructuralLinks(kunde, uriInfo);
		if (kunde == null) {
			throw new NotFoundException("kunde.notFound.id", id);	
		}
		
		return Response.ok(kunde)
                       .links(getTransitionalLinks(kunde, uriInfo))
                       .build();
	}
	
	// Fehlersuche @Path
	//public void setStructuralLinks(Kunde kunde, UriInfo uriInfo) {
	// URI fuer Bestellungen setzen
	//final URI uri = getUriBestellungen(kunde, uriInfo);
	//kunde.setBestellungenUri(uri);
	//}
	
	// Fehlersuche @Path
	//private URI getUriBestellungen(Kunde kunde, UriInfo uriInfo) {
	//	return uriHelper.getUri(KundeResource.class, "findBestellungenByKundeId", kunde.getId(), uriInfo);
	//}
	
	public Link[] getTransitionalLinks(Kunde kunde, UriInfo uriInfo) {
		final Link self = Link.fromUri(getUriKunde(kunde, uriInfo))
	                          .rel(SELF_LINK)
	                          .build();
		final Link list = Link.fromUri(uriHelper.getUri(KundeResource.class, uriInfo))
                .rel(LIST_LINK)
                .build();

		final Link add = Link.fromUri(uriHelper.getUri(KundeResource.class, uriInfo))
               .rel(ADD_LINK)
               .build();

		final Link update = Link.fromUri(uriHelper.getUri(KundeResource.class, uriInfo))
                  .rel(UPDATE_LINK)
                  .build();

		final Link remove = Link.fromUri(uriHelper.getUri(KundeResource.class, "deleteKunde", kunde.getId(), uriInfo))
                  .rel(REMOVE_LINK)
                  .build();

		return new Link[] { self, list, add, update, remove };
	}
	
	public URI getUriKunde(Kunde kunde, UriInfo uriInfo) {
		return uriHelper.getUri(KundeResource.class, "findKundeById", kunde.getId(), uriInfo);
	}
	
	@GET
	public Response findKunden(@QueryParam(KUNDEN_NACHNAME_QUERY_PARAM)
   	                           @Pattern(regexp = NAME_PATTERN, message = "{kunde.nachname.pattern}")
							   String nachname,
							   @QueryParam(KUNDEN_EMAIL_QUERY_PARAM)
   	                           @Email(message = "{kunde.email}")
							   String email,
							   @QueryParam(KUNDEN_PLZ_QUERY_PARAM)
   	                           @Pattern(regexp = "\\d{5}", message = "{adresse.plz}")
							   String plz) {
		List<? extends Kunde> kunden = null;
		Kunde kunde = null;
		if (nachname != null) {
			kunden = ks.findKundenByNachname(nachname);
		}
		else if (email != null) {
			kunde = ks.findKundeByEmail(email);
		}
		else if (plz != null) {
			// TODO Beispiel fuer ein TODO bei fehlender Implementierung
			throw new RuntimeException("Suche nach PLZ noch nicht implementiert");
		}
		else {
			kunden = ks.findAllKunden();
		}
		
		Object entity = null;
		Link[] links = null;
		if (kunden != null) {
			
			// Fehlersuche @Path
			//for (Kunde k : kunden) {
			//	setStructuralLinks(k, uriInfo);
			//}
			
			
			// FIXME JDK 8 hat Lambda-Ausdruecke, aber Proxy-Klassen von Weld funktionieren noch nicht mit Lambda-Ausdruecken
			//kunden.parallelStream()
			//      .forEach(k -> setStructuralLinks(k, uriInfo));
			entity = new GenericEntity<List<? extends Kunde>>(kunden){};
			links = getTransitionalLinksKunden(kunden, uriInfo);
		}
		else if (kunde != null) {
			entity = kunde;
			links = getTransitionalLinks(kunde, uriInfo);
		}
		
		return Response.ok(entity)
                       .links(links)
                       .build();
	}
	
	private Link[] getTransitionalLinksKunden(List<? extends Kunde> kunden, UriInfo uriInfo) {
		if (kunden == null || kunden.isEmpty()) {
			return null;
		}
		
		final Link first = Link.fromUri(getUriKunde(kunden.get(0), uriInfo))
	                           .rel(FIRST_LINK)
	                           .build();
		final int lastPos = kunden.size() - 1;
		final Link last = Link.fromUri(getUriKunde(kunden.get(lastPos), uriInfo))
                              .rel(LAST_LINK)
                              .build();
		
		return new Link[] { first, last };
	}
	
	public List<Kunde> findAllKunden() {
		// TODO Datenbanzugriffsschicht statt Mock
		final List<Kunde> kundenliste = Mock.findAllKunden();
		if (kundenliste == null) {
			throw new NotFoundException("kunde.notFound.all");
		}
		return kundenliste;
	}
	
	public List<Kunde> findKundenByNachname(String nachname) {
		// TODO Datenbanzugriffsschicht statt Mock
		List<Kunde> kunden = Mock.findKundenByNachname(nachname);
		return kunden;
	}
	
	@POST
	@Consumes({ APPLICATION_JSON, APPLICATION_XML, TEXT_XML })
	@Produces
	public Response createKunde(@Valid Kunde kunde) {
		// Rueckwaertsverweis von Adresse zu Kunde setzen
		kunde.getAdresse().setKunde(kunde);
		kunde = ks.createKunde(kunde);
		
		return Response.created(getUriKunde(kunde, uriInfo))
			           .build();
	}
	
	@PUT
	@Consumes({ APPLICATION_JSON, APPLICATION_XML, TEXT_XML })
	@Produces
	public void updateKunde(@Valid Kunde kunde) {
		ks.updateKunde(kunde);
	}
	
	@DELETE
	@Path("{id:[1-9][0-9]*}")
	@Produces
	public void deleteKunde(@PathParam("id") Long kundeId) {
		ks.deleteKunde(kundeId);
	}

}
